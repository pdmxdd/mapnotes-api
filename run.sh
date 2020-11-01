#! /usr/bin/env bash

run_gradle_task() {
  source "$env_file"

  ./gradlew "$gradle_task" \
    -D "MAPNOTES_API_DB_PORT=$MAPNOTES_API_DB_PORT" \
    -D "MAPNOTES_API_DB_HOST=$MAPNOTES_API_DB_HOST" \
    -D "MAPNOTES_API_DB_NAME=$MAPNOTES_API_DB_NAME" \
    -D "MAPNOTES_API_DB_USER=$MAPNOTES_API_DB_USER" \
    -D "MAPNOTES_API_DB_PASSWORD=$MAPNOTES_API_DB_PASSWORD"
}

run() {

  local env="$1"
  local env_file=".${env}.env"

  local gradle_task
  if [[ "$env" == 'test' ]]; then
    gradle_task='test'
  elif [[ "$env" == 'dev' ]]; then
    gradle_task='bootRun'
  else
    echo "invalid environment: $env"
    echo "rerun the script passing 'dev' or 'test' environment"
    exit 1
  fi

  echo 'starting backing services'
  local compose_args="--env-file $env_file"
  eval docker-compose "$compose_args" up -d

  echo 'waiting for docker services to start...'
  sleep 1.5s

  echo "executing gradle task: $gradle_task"
  run_gradle_task

  local gradle_exit_status="$?"
  if [[ "$gradle_exit_status" -ne 0 ]]; then
    if [[ "$env" == 'test' ]]; then
      echo 'tests failed, test output available at http://localhost:4000'
      echo 'press enter to shut down the backing services and exit'
      read -n 1
    else
      echo 'all tests passed!'
    fi
  fi

  echo 'shutting down backing services'

  local compose_command
  if [[ "$env" == 'dev' ]]; then
    echo 'dev environment will stop but not remove service containers (to persist data)'
    compose_command='stop'
  else
    echo 'test environment will stop and remove service containers (to not persist data between tests)'
    compose_command='down'
  fi

  eval docker-compose "$compose_args" "$compose_command"
}

run "$1"
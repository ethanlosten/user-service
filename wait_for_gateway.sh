#!/bin/bash

url="$1"
shift
cmd="$@"

echo "Waiting for registry to register the gateway..."

while [[ $(curl {$url}) != *"GATEWAY"* ]]
do
    sleep 5
done

echo "Gateway is up, launching user service."

sleep 10

exec $cmd

#!/bin/bash

apt update && apt install -y sudo

echo "root:senhaforte123" | chpasswd

usermod -aG sudo root

cd SambaWebAPI/

./gradlew test

echo "script executado com sucesso"

tail -f /dev/null

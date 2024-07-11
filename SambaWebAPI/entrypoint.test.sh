#!/bin/bash

# Criar usuário sambauser com senha sambawebapi!@#
useradd -m -s /bin/bash -p sambawebapi123 sambauser
usermod -aG sudo sambauser

# Configuração do sudo
echo "sambauser ALL=(ALL:ALL) ALL" >> /etc/sudoers

cd SambaWeb/SambaWebAPI
./gradlew test

#!/bin/bash

# Criar usuário testeIntegration
useradd -m -d /home/testeIntegration testeIntegration

# Definir senha para o usuário testeIntegration
echo "testeIntegration:senhaforte123" | chpasswd

# Adicionar usuário testeIntegration ao grupo sudo
usermod -aG sudo testeIntegration

# Configurar sudo para pedir senha do usuário testeIntegration
echo "testeIntegration ALL=(ALL) ALL" >> /etc/sudoers

# Logar no usuário testeIntegration
cd SambaWebAPI/
./gradlew test
echo "script executado com sucesso"

tail -f /dev/null

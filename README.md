# Samba Web Dashboard

Este é um projeto que consiste em uma aplicação web para gerenciar um servidor Samba através de uma interface construída em Angular. A aplicação interage com uma API Spring Boot, que por sua vez executa os comandos necessários no servidor Samba, encapsulado em um contêiner Docker.

## Funcionalidades Principais

- **Dashboard**: Interface intuitiva que exibe informações importantes sobre o servidor Samba, como status, usuários conectados, espaço em disco, etc.
  
- **Gerenciamento de Compartilhamentos**: Permite criar, editar e excluir compartilhamentos de arquivos e diretórios no servidor Samba.
  
- **Controle de Usuários e Permissões**: Facilita a administração de usuários, grupos e permissões de acesso aos compartilhamentos.
  
- **Logs e Monitoramento**: Visualização dos logs do servidor Samba para acompanhamento e diagnóstico de problemas.

## Tecnologias Utilizadas

- **Angular**: Framework de desenvolvimento front-end utilizado para construir a interface do usuário.
  
- **Spring Boot**: Framework Java utilizado para construir a API REST que interage com o servidor Samba.
  
- **Docker**: Utilizado para encapsular o servidor Samba, garantindo portabilidade e isolamento.

## Como Usar

1. **Clonar o Repositório**:

   ```
   git clone https://github.com/seu-usuario/samba-web-dashboard.git```

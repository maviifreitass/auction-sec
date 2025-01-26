# Sistema de Leilão Virtual
Projeto desenvolvido para implementar uma plataforma de leilão virtual segura e eficiente, utilizando Java. 

O sistema segue uma arquitetura cliente-servidor baseada em comunicação multicast e emprega um esquema de criptografia híbrida para garantir a privacidade dos dados e a autenticidade dos usuários.

## Funcionalidades
-Leilões em Tempo Real:
- Lances são processados e transmitidos em tempo real utilizando comunicação multicast.

## Criptografia Híbrida:
- Combina criptografia simétrica e assimétrica para proteger as trocas de dados.
- A criptografia simétrica garante transferência de mensagens rápida e segura.
- A criptografia assimétrica assegura a autenticação e o compartilhamento seguro de chaves.

## Privacidade e Autenticidade:
Todas as mensagens e dados transmitidos entre os usuários e o servidor são criptografados, garantindo que apenas os usuários autorizados tenham acesso.

## Tecnologias Utilizadas
- Linguagem de Programação: Java
- Criptografia:
- RSA (Criptografia Assimétrica)
- AES (Criptografia Simétrica)
- Comunicação Multicast: Para garantir a comunicação eficiente entre os clientes e o servidor.
- JSON: Para troca de mensagens estruturadas entre clientes e servidor.

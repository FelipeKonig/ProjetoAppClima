# ProjetoAppClima

Projeto desenvolvido com o formato cliente-servidor, com o uso das bibliotecas javaFX e Spring. Uso de javaFX para criação das interfaces, e Spring para comunicação entre os clientes e o servidor.

O projeto possui três partes. A parte do servidor que consiste em armazenar, adicionar, excluir e alterar os climas em um HashMap, que exemplifica um banco de dados. Além de desempenhar o papel de enviar e receber informações dos seus respectivos clientes, com uso do Spring. Outra parte do projeto é o cliente que desempenha o único papel de enviar automaticamente uma nova requisição para o servidor adicionar um clima, com o intervalo de 10 segundos por envio. E por fim, a parte do projeto que executa o segundo cliente, parte responsável por mostrar ao usuário as informações do clima com uso de interface gráfica, além de permitir ao usuário poder selecionar, editar ou excluir algum clima armazenado no servidor, tudo por meio de requisições.   

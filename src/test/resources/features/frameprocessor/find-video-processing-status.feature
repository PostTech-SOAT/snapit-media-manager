# language: pt

Funcionalidade: Consulta de status de processamento de vídeo
  Como um usuário eu quero consultar o status de processamento dos meus vídeos
  Para acompanhar o progresso

  Cenário: Encontrar status de processamento pelo e-mail
    Dado que existem vídeos processados e em processamento para o e-mail "email@test.com"
    Quando o usuário consulta o status de processamento
    Então o sistema deve retornar os status dos vídeos
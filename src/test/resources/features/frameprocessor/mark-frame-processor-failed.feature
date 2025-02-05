# language: pt

Funcionalidade: Marcar processador de frames como falhado
  Como um sistema de processamento de vídeos
  Eu quero marcar um processamento como falhado
  Para registrar que houve um erro

  Cenário: Marcação de falha no processamento
    Dado que um vídeo está sendo processado
    Quando ocorre uma falha no processamento
    Então o sistema deve marcar o processamento como falhado
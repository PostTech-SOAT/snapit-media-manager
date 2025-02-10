# language: pt

Funcionalidade: Upload de Vídeo
  Como um usuário eu quero fazer upload de um vídeo para que ele seja processado

  Cenário: Upload de vídeo com sucesso
    Dado que um vídeo válido é enviado para upload
    Quando o usuário faz o upload
    Então o sistema deve armazenar o vídeo e iniciar o processamento

  Cenário: Erro ao tentar fazer upload de um vídeo já existente
    Dado que já existe um vídeo com o mesmo nome para o e-mail "email@test.com"
    Quando o usuário tenta fazer o upload novamente
    Então deve ser lançada uma exceção de conflito
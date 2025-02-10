# language: pt

Funcionalidade: Download de vídeo
  Como um usuário eu quero fazer o download de um vídeo

  Cenário: Download de vídeo com sucesso
    Dado que existe um vídeo salvo para o ID "123e4567-e89b-12d3-a456-426614174000"
    Quando o usuário solicita o download do vídeo
    Então o vídeo deve ser retornado com sucesso
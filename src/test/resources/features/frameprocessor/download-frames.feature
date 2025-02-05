# language: pt

Funcionalidade: Download de Frames
  Como um usuário eu quero baixar frames de um vídeo
  Para que eu possa utilizá-los posteriormente

  Cenário: Download de frames com sucesso
    Dado que o banco de dados contém um arquivo de frames para o ID "123e4567-e89b-12d3-a456-426614174000"
    E o serviço S3 possui o arquivo correspondente
    Quando o usuário solicita o download dos frames
    Então o download deve ser concluído com sucesso

  Cenário: Erro ao tentar baixar frames que não existem
    Dado que o banco de dados não contém um arquivo de frames para o ID "123e4567-e89b-12d3-a456-426614174000"
    Quando o usuário solicita o download dos frames
    Então deve ser lançada uma exceção de recurso não encontrado
# language: pt
Funcionalidade: Marcar processador de frames como finalizado
  Como um sistema de processamento de vídeos
  Eu quero marcar um processador de frames como finalizado
  Para indicar que o processamento do vídeo foi concluído com sucesso

  Cenário: Marcar um processador de frames como finalizado com sucesso
    Dado um processador de frames com ID válido
    E um arquivo de vídeo processado chamado "video_finalizado.zip"
    Quando o sistema marca o processador de frames como finalizado
    Então o status do processador de frames deve ser atualizado para finalizado
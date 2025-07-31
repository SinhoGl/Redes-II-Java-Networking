/*
  =====================================================================================
  Resposta sobre a Ordem de Execução de Clientes e Servidores (TCP vs.UDP)
  =====================================================================================
 
  A necessidade de o servidor ser executado antes do cliente (ou a falta dela)
  é uma consequência direta da natureza fundamental de cada protocolo.
 
  -------------------------------------------------------------------------------------
 
  Pergunta 1: Para uma aplicação cliente-servidor TCP, por que o programa
              servidor deve ser executado antes do programa cliente?
 
  Resposta:
  O TCP (Transmission Control Protocol) é um protocolo orientado à conexão.
  Isso significa que, antes de qualquer troca de dados, uma conexão formal e
  confiável deve ser estabelecida entre o cliente e o servidor. Esse processo
  é conhecido como "three-way handshake" (aperto de mão de três vias).
 
  O fluxo é o seguinte:
  1.  Servidor Escuta: O programa servidor primeiro se vincula a um endereço IP
      e a uma porta específica (ex: 8080). Em seguida, ele entra em um estado de
      escuta passiva, aguardando ativamente por tentativas de conexão de clientes.
      Sem o servidor neste estado, não há nenhum processo na máquina de destino
      pronto para aceitar uma nova conexão naquela porta.
 
  2.  Cliente Tenta Conectar: O cliente, conhecendo o IP e a porta do servidor,
      inicia o processo de conexão enviando um pacote inicial (SYN) para o servidor.
 
  3.  Conexão Estabelecida: O servidor, que já estava escutando, recebe o pedido,
      responde (SYN-ACK) e, após a confirmação final do cliente (ACK), a conexão
      é considerada estabelecida.
 
  Conclusão: Se o cliente tentar se conectar ANTES de o servidor estar no estado
  de escuta, sua solicitação de conexão chegará a uma porta onde nenhum processo
  está esperando por ela. O sistema operacional do servidor recusará ativamente
  a conexão, resultando em um erro no lado do cliente (como uma
  'ConnectionRefusedException' em Java). É como ligar para alguém cujo telefone
  está desligado; a chamada simplesmente não completa.
 
  -------------------------------------------------------------------------------------
 
  Pergunta 2: Para uma aplicação cliente-servidor UDP, por que o programa
              cliente pode ser executado antes do programa servidor?
 
  Resposta:
  O UDP (User Datagram Protocol) é um protocolo não orientado à conexão
  (connectionless). Ele não estabelece um canal de comunicação prévio. Em vez disso,
  ele opera em um modelo de "enviar e esquecer".
 
  O fluxo é muito mais simples:
  1.  Cliente Envia: O cliente cria um pacote de dados (datagrama), que inclui
      a mensagem e o endereço de destino (IP e porta). Em seguida, ele simplesmente
      envia esse pacote para a rede. O cliente não realiza nenhuma verificação
      para saber se há um servidor pronto para receber; ele apenas envia.
 
  2.  Servidor (Talvez) Recebe: Se um servidor estiver em execução e escutando
      na porta de destino no momento em que o pacote chega, ele o processará.
 
  Conclusão: Como o cliente UDP não precisa de uma confirmação de que o servidor
  está pronto, a operação de envio do lado do cliente não falha se o servidor
  estiver offline. O cliente pode ser executado primeiro, enviar seus pacotes e
  encerrar sua execução sem erros. No entanto, se o servidor não estiver ativo
  para receber esses pacotes no momento em que eles chegam, os pacotes serão
  simplesmente descartados pela rede ou pelo sistema operacional do destino,
  e a informação será perdida. O cliente não é notificado dessa falha na entrega.
 
 */
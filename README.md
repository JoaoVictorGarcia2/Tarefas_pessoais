# João Victor Garcia
# Leonardo Henrique Bernardes de Souza

# Tarefas_pessoais.
Gerenciador de tarefas.

# Para funcionar
Gere o banco de dados necessario e após isso configure o application.properties com seus dados.

# Comandos para gerar o banco de dados.
CREATE SCHEMA todolist;

CREATE TABLE todolist.tarefa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    observacoes TEXT,
    status VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP
);

# Comando para buscar todas as tarefas.
SELECT * FROM todolist.tarefa;

# Comando para rodar o projeto
mvn spring-boot:run 
Após isso o projeto estara rodando e sera possivel realizar GET,POST,UPDATE,DELETE como desejado dentro do projeto tem um HTML onde possui uma interfaçe basico onde se pode realizar essas ações facilmente.

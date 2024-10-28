import mysql.connector
from mysql.connector import Error
import random
from datetime import datetime, timedelta

def create_connection(host_name, user_name, user_password, db_name, port):
    connection = None
    try:
        connection = mysql.connector.connect(
            host=host_name,
            user=user_name,
            passwd=user_password,
            database=db_name,
            port=port
        )
        print("Connection to MySQL DB successful")
    except Error as e:
        print(f"The error '{e}' occurred")
    return connection

def execute_query(connection, query):
    cursor = connection.cursor()
    try:
        cursor.execute(query)
        connection.commit()
        print("Query executed successfully")
    except Error as e:
        print(f"The error '{e}' occurred")

# Detalhes da conexão
host_name = "127.0.0.1"  # Conectando ao host local
user_name = "root"
user_password = "root"
db_name = "controle_de_estoque"
port = 3307  # Porta mapeada no host

# Criar conexão
connection = create_connection(host_name, user_name, user_password, db_name, port)

# Verificar se a conexão foi bem-sucedida
if connection is None:
    print("Failed to connect to the database. Exiting...")
    exit(1)

execute_query(connection, "SET FOREIGN_KEY_CHECKS = 0;")

# Populando a tabela usuario
for i in range(100):
    nome = f"Usuario{i}"
    sobrenome = f"Sobrenome{i}"
    tipo_usuario = random.choice(['admin', 'user'])
    email = f"usuario{i}@example.com"
    senha = "senha123"
    query = f"""
    INSERT INTO usuario (nome, sobrenome, tipo_usuario, email, senha) 
    VALUES ('{nome}', '{sobrenome}', '{tipo_usuario}', '{email}', '{senha}');
    """
    execute_query(connection, query)

# Populando a tabela categoria
categorias = ['Eletrônicos', 'Móveis', 'Ferramentas']
for categoria in categorias:
    query = f"INSERT INTO categoria (nome) VALUES ('{categoria}');"
    execute_query(connection, query)

# Populando a tabela localizacao
localizacoes = ['Armazém 1', 'Armazém 2', 'Armazém 3']
for localizacao in localizacoes:
    query = f"INSERT INTO localizacao (nome) VALUES ('{localizacao}');"
    execute_query(connection, query)

# Populando a tabela modelo
modelos = ['Modelo A', 'Modelo B', 'Modelo C']
for modelo in modelos:
    query = f"INSERT INTO modelo (nome) VALUES ('{modelo}');"
    execute_query(connection, query)

# Populando a tabela anexos
anexos = [
    ('Manual de Instruções', '/path/to/manual.pdf'),
    ('Certificado de Garantia', '/path/to/certificado.pdf')
]
for nome, caminho in anexos:
    query = f"INSERT INTO anexos (nome, caminho) VALUES ('{nome}', '{caminho}');"
    execute_query(connection, query)

# Populando a tabela item
for i in range(1000):
    descricao = f"Item {i}"
    localizacao_atual = f"Armazém {random.randint(1, 3)}"
    potencia = random.randint(50, 500)
    patrimonio = f"PAT{i:05d}"
    numero_de_serie = f"SN{i:05d}"
    numero_nota_fiscal = f"NF{i:05d}"
    comentario_manutencao = "Em bom estado"
    data_entrada = (datetime.now() - timedelta(days=random.randint(0, 365))).strftime('%Y-%m-%d')
    ultima_qualificacao = (datetime.now() - timedelta(days=random.randint(0, 180))).strftime('%Y-%m-%d')
    data_nota_fiscal = (datetime.now() - timedelta(days=random.randint(0, 365))).strftime('%Y-%m-%d')
    proxima_qualificacao = (datetime.now() + timedelta(days=random.randint(0, 365))).strftime('%Y-%m-%d')
    prazo_manutencao = (datetime.now() + timedelta(days=random.randint(0, 365))).strftime('%Y-%m-%d')
    estado = random.choice(['Disponível', 'Emprestado', 'Manutenção'])
    categoria_id_categoria = random.randint(1, 3)
    status_item = random.choice(['Novo', 'Usado'])
    modelo_id_modelo = random.randint(1, 3)
    localizacao_id_localizacao = random.randint(1, 3)

    query = f"""
    INSERT INTO item (descricao, localizacao_atual, potencia, patrimonio, numero_de_serie, numero_nota_fiscal, comentario_manutencao, data_entrada, ultima_qualificacao, data_nota_fiscal, proxima_qualificacao, prazo_manutencao, estado, categoria_id_categoria, status_item, modelo_id_modelo, localizacao_id_localizacao) 
    VALUES ('{descricao}', '{localizacao_atual}', {potencia}, '{patrimonio}', '{numero_de_serie}', '{numero_nota_fiscal}', '{comentario_manutencao}', '{data_entrada}', '{ultima_qualificacao}', '{data_nota_fiscal}', '{proxima_qualificacao}', '{prazo_manutencao}', '{estado}', {categoria_id_categoria}, '{status_item}', {modelo_id_modelo}, {localizacao_id_localizacao});
    """
    execute_query(connection, query)

# Populando a tabela acao
for i in range(100):
    entidade = f"Empresa {i}"
    ra_rna = f"RA{i:03d}"
    data_emprestimo = (datetime.now() - timedelta(days=random.randint(0, 365))).strftime('%Y-%m-%d')
    data_devolucao = (datetime.now() + timedelta(days=random.randint(0, 365))).strftime('%Y-%m-%d')
    usuario_id_usuario = random.randint(1, 100)
    item_id_item = random.randint(1, 1000)
    tipo_acao = random.randint(1, 3)
    anexos_id_anexos = random.randint(1, 2)
    localizacao_id_localizacao = random.randint(1, 3)

    query = f"""
    INSERT INTO acao (entidade, ra_rna, data_emprestimo, data_devolucao, usuario_id_usuario, item_id_item, tipo_acao, anexos_id_anexos, localizacao_id_localizacao) 
    VALUES ('{entidade}', '{ra_rna}', '{data_emprestimo}', '{data_devolucao}', {usuario_id_usuario}, {item_id_item}, {tipo_acao}, {anexos_id_anexos}, {localizacao_id_localizacao});
    """
    execute_query(connection, query)
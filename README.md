# HomeAway From Home  
**Projeto de Estruturas de Dados e Algoritmos (AED) 2025/26**

---

## 📘 Descrição Geral

**HomeAway From Home** é uma aplicação em Java que apoia estudantes internacionais numa cidade universitária, fornecendo informações sobre serviços locais — **alimentação, alojamento e lazer** — organizados geograficamente.  
O projeto visa aplicar **estruturas de dados e algoritmos** na construção de um sistema interativo baseado em comandos.

Cada área geográfica é delimitada por um **retângulo de coordenadas** (latitude e longitude) e contém uma coleção de **serviços** e **estudantes**. O programa gere inserções, consultas, movimentos e avaliações destes elementos.

---

## 🧩 Estrutura do Projeto

O trabalho é realizado em **duas fases**:

| Fase | Entrega | Peso | Descrição |
|------|----------|------|------------|
| **1ª fase** | Até **31/10/2025** | 13% | Implementação inicial do sistema usando o pacote `dataStructures` fornecido. |
| **2ª fase** | Até **05/12/2025** | 22% | Extensão do sistema com novas estruturas e funcionalidades. |

Cada fase inclui:
- **Diagrama de classes (1%)**  
- **Programa funcional (10% / 16%)**  
- **Relatório final (2% / 5%)**

Todos os programas são submetidos via **Mooshak** e devem respeitar as **interfaces fornecidas**, sem uso de `java.util` (exceto `Scanner` para I/O).

---

## 🧠 Conceitos Envolvidos

- Estruturas de dados lineares e hierárquicas (listas, árvores, tabelas de hash, etc.)
- Gestão de entidades com relações e atributos.
- Organização espacial (limites geográficos).
- Processamento de comandos e tratamento de erros em sequência definida.
- Avaliações, ordenações e cálculos de distância (Manhattan Distance).

---

## 👥 Tipos de Entidades

### **Serviços**
- **Eating:** custo do menu e capacidade.  
- **Lodging:** custo mensal e número de quartos.  
- **Leisure:** preço do bilhete e desconto estudantil.  
Todos os serviços têm **nome, latitude e longitude**.

### **Estudantes**
- **Bookish:** foca-se em locais de lazer.  
- **Outgoing:** visita qualquer tipo de serviço.  
- **Thrifty:** procura sempre as opções mais baratas.  

Cada estudante tem **nome, país de origem e alojamento atual**.

---

## 💻 Comandos Principais

| Comando | Função |
|----------|--------|
| `bounds` | Define a área geográfica ativa. |
| `save` / `load` | Guarda ou carrega áreas de ficheiro. |
| `service` / `services` | Adiciona ou lista serviços. |
| `student` / `students` | Regista ou lista estudantes. |
| `leave` | Remove um estudante. |
| `go` / `move` | Muda localização ou alojamento. |
| `users` | Lista utilizadores num serviço. |
| `where` | Mostra a localização de um estudante. |
| `visited` | Lista locais visitados. |
| `star` / `ranking` / `ranked` | Gere e ordena avaliações. |
| `tag` | Pesquisa por palavra nas reviews. |
| `find` | Encontra o serviço mais relevante. |
| `help` / `exit` | Ajuda e encerramento do programa. |

Todos os comandos são **case-insensitive** e produzem mensagens específicas de sucesso ou erro.

---

## 🧮 Avaliação e Submissão

- O programa deve obter **100 pontos no Mooshak** para ser avaliado.  
- **Funcionalidade (20%)** + **Qualidade do código (80%)**.  
- Discussão oral obrigatória em cada fase.  
- Submissão com cabeçalho nos ficheiros:

```java
/**
 * @author Nome1 (Número1) email1
 * @author Nome2 (Número2) email2
 */
```

Nome do grupo no Mooshak = números dos alunos concatenados (ex: `5677_5678`).

---

## ▶️ Execução

1. Compilar o projeto:
   ```bash
   javac *.java
   ```
2. Executar:
   ```bash
   java Main
   ```
3. Usar os comandos descritos no terminal.

Os ficheiros de dados são guardados na pasta `data/`.

---

## 📎 Referências
- Projeto AED 2025/26 – Departamento de Informática, FCT NOVA.  
- [Definição da distância de Manhattan (NIST)](https://xlinux.nist.gov/dads/HTML/manhattanDistance.html)

---

**Autores:**  
Grupo nºXX – Nome1, Nome2  
FCT NOVA – AED 2025/26

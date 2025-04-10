document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('formTarefa');
    const listaTarefas = document.getElementById('listaTarefas');
    let editando = false;
    let tarefaId = null;
    
    carregarTarefas();
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const tarefa = {
            nome: document.getElementById('nome').value,
            descricao: document.getElementById('descricao').value,
            status: document.getElementById('status').value,
            observacoes: document.getElementById('observacoes').value
        };
        
        const url = editando ? `http://localhost:8080/api/tarefas/${tarefaId}` : 'http://localhost:8080/api/tarefas';
        const method = editando ? 'PUT' : 'POST';
        
        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tarefa)
        })
        .then(response => response.json())
        .then(data => {
            alert(`Tarefa ${editando ? 'atualizada' : 'adicionada'} com sucesso!`);
            form.reset();
            editando = false;
            tarefaId = null;
            document.querySelector('button[type="submit"]').textContent = 'Salvar Tarefa';
            carregarTarefas();
        })
        .catch(error => {
            console.error('Erro:', error);
            alert(`Erro ao ${editando ? 'atualizar' : 'adicionar'} tarefa`);
        });
    });
    
    function carregarTarefas() {
        fetch('http://localhost:8080/api/tarefas')
            .then(response => response.json())
            .then(tarefas => {
                listaTarefas.innerHTML = '';
                
                if (tarefas.length === 0) {
                    listaTarefas.innerHTML = '<p>Nenhuma tarefa encontrada.</p>';
                    return;
                }
                
                tarefas.forEach(tarefa => {
                    const div = document.createElement('div');
                    div.className = 'tarefa';
                    div.innerHTML = `
                        <h3>${tarefa.nome} - <small>${tarefa.status}</small></h3>
                        <p class="tarefa-info"><strong>Descrição:</strong> ${tarefa.descricao || 'Nenhuma'}</p>
                        <p class="tarefa-info"><strong>Observações:</strong> ${tarefa.observacoes || 'Nenhuma'}</p>
                        <div class="tarefa-data">
                            <p><strong>Criada em:</strong> ${formatarData(tarefa.dataCriacao)}</p>
                            <p><strong>Última atualização:</strong> ${tarefa.dataAtualizacao ? formatarData(tarefa.dataAtualizacao) : 'Nunca foi atualizada'}</p>
                        </div>
                        <div class="botoes-acao">
                            <button class="btn-editar" onclick="editarTarefa(${tarefa.id})">Editar</button>
                            <button class="btn-excluir" onclick="excluirTarefa(${tarefa.id})">Excluir</button>
                        </div>
                    `;
                    listaTarefas.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Erro:', error);
                listaTarefas.innerHTML = '<p>Erro ao carregar tarefas.</p>';
            });
    }
    
    function formatarData(data) {
        if (!data) return 'Data inválida';
        
        try {
            const date = new Date(data);
            return date.toLocaleDateString('pt-BR', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            });
        } catch (e) {
            console.error('Erro ao formatar data:', e);
            return 'Data inválida';
        }
    }
    
    window.editarTarefa = function(id) {
        fetch(`http://localhost:8080/api/tarefas/${id}`)
            .then(response => response.json())
            .then(tarefa => {
                document.getElementById('nome').value = tarefa.nome;
                document.getElementById('descricao').value = tarefa.descricao || '';
                document.getElementById('status').value = tarefa.status;
                document.getElementById('observacoes').value = tarefa.observacoes || '';
                
                editando = true;
                tarefaId = id;
                document.querySelector('button[type="submit"]').textContent = 'Atualizar Tarefa';
                window.scrollTo(0, 0);
            });
    };
    
    window.excluirTarefa = function(id) {
        if (confirm('Tem certeza que deseja excluir esta tarefa?')) {
            fetch(`http://localhost:8080/api/tarefas/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    alert('Tarefa excluída com sucesso!');
                    carregarTarefas();
                } else {
                    throw new Error('Erro ao excluir tarefa');
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao excluir tarefa');
            });
        }
    };
});
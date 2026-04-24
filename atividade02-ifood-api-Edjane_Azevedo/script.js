
    const API = 'http://localhost:8080';

    function mostrarResultado(dados) {
      const elemento = document.getElementById('resultado');
      if (elemento) {
        elemento.textContent = JSON.stringify(dados, null, 2);
      }
    }

    async function requisicao(url, opcoes = {}) {
      const resposta = await fetch(url, {
        headers: { 'Content-Type': 'application/json' },
        ...opcoes
      });
      const dados = await resposta.json().catch(() => ({}));
      if (!resposta.ok) throw dados;
      return dados;
    }

    function mostrarLista(lista, tipo) {
      const div = document.getElementById('lista');
      if (!Array.isArray(lista) || lista.length === 0) {
        div.innerHTML = '<div class="empty">Nenhum registro encontrado.</div>';
        return;
      }
      div.innerHTML = lista.map(item => {
        if (tipo === 'clientes') {
          return `<div class="item"><span class="badge">Cliente</span><div class="item-title">${item.nome}</div><div class="muted"><strong>ID:</strong> ${item.id}<br><strong>Email:</strong> ${item.email}<br><strong>Pedidos:</strong> ${item.quantidadePedidos ?? 0}</div><button onclick='preencherCliente(${JSON.stringify(item)})'>Editar</button><button class="btn-danger" onclick="deletarRegistro('clientes', ${item.id}, listarClientes)">Excluir</button></div>`;
        }
        if (tipo === 'restaurantes') {
          return `<div class="item"><span class="badge">Restaurante</span><div class="item-title">${item.nome}</div><div class="muted"><strong>ID:</strong> ${item.id}<br><strong>Categoria:</strong> ${item.categoria}<br><strong>Produtos:</strong> ${item.quantidadeProdutos ?? 0}</div><button onclick='preencherRestaurante(${JSON.stringify(item)})'>Editar</button><button class="btn-danger" onclick="deletarRegistro('restaurantes', ${item.id}, listarRestaurantes)">Excluir</button></div>`;
        }
        if (tipo === 'produtos') {
          return `<div class="item"><span class="badge">Produto</span><div class="item-title">${item.nome}</div><div class="muted"><strong>ID:</strong> ${item.id}<br><strong>Preço:</strong> R$ ${item.preco}<br><strong>Restaurante:</strong> ${item.restauranteNome} (ID ${item.restauranteId})</div><button onclick='preencherProduto(${JSON.stringify(item)})'>Editar</button><button class="btn-danger" onclick="deletarRegistro('produtos', ${item.id}, listarProdutos)">Excluir</button></div>`;
        }
        return `
          <div class="item">
            <span class="badge">Pedido</span>
            <div class="item-title">Pedido #${item.id ?? ''}</div>
            <div class="muted">
              <strong>Status:</strong> ${item.status ?? ''}<br>
              <strong>Cliente:</strong> ${item.clienteNome ?? ''} ${item.clienteId ? `(ID ${item.clienteId})` : ''}<br>
              <strong>Valor total:</strong> R$ ${item.valorTotal ?? ''}<br>
              <strong>Produtos:</strong> ${Array.isArray(item.produtos) ? item.produtos.join(', ') : ''}
            </div>
            <button onclick='preencherPedido(${JSON.stringify(item)})'>Editar</button>
            <button class="btn-danger" onclick="deletarRegistro('pedidos', ${item.id}, listarPedidos)">Excluir</button>
          </div>`;
      }).join('');
    }

    async function deletarRegistro(tipo, id, atualizarLista) {
      if (!confirm('Tem certeza que deseja excluir este registro?')) return;
      try {
        const resposta = await fetch(`${API}/${tipo}/${id}`, { method: 'DELETE' });
        if (resposta.status === 204) {
          mostrarResultado({ mensagem: `${tipo} excluído com sucesso!` });
          atualizarLista();
          return;
        }
        const erro = await resposta.json().catch(() => ({ erro: 'Erro ao excluir.' }));
        mostrarResultado(erro);
      } catch (erro) {
        mostrarResultado({ erro: 'Erro ao excluir', detalhes: erro.message });
      }
    }

    async function buscarClientePorNome(nome) {
      const resposta = await fetch(`${API}/clientes?nome=${encodeURIComponent(nome)}`);
      const clientes = await resposta.json();
      if (!clientes.length) throw { erro: 'Cliente não encontrado' };
      return clientes[0];
    }

    async function criarCliente() {
      try {
        const dados = await requisicao(`${API}/clientes`, {
          method: 'POST',
          body: JSON.stringify({
            nome: clienteNome.value,
            email: clienteEmail.value
          })
        });

        listarClientes();
        mostrarResultado(dados);
        limparCliente();


      } catch (erro) {
        mostrarResultado(erro);
      }
    }
    function limparCliente() {
      clienteId.value = "";
      clienteNome.value = "";
      clienteEmail.value = "";
    }
    async function listarClientes() {
      try { mostrarLista(await requisicao(`${API}/clientes`), 'clientes'); } catch (erro) { mostrarResultado(erro); }
    }
    function preencherCliente(item) { clienteId.value = item.id; clienteNome.value = item.nome; clienteEmail.value = item.email; }
    async function atualizarCliente() {
      try {
        if (!clienteId.value) return mostrarResultado({ erro: 'Clique em Editar em algum cliente antes de atualizar.' });
        const dados = await requisicao(`${API}/clientes/${clienteId.value}`, { method: 'PUT', body: JSON.stringify({ nome: clienteNome.value, email: clienteEmail.value }) });
        mostrarResultado(dados); listarClientes();
      } catch (erro) { mostrarResultado(erro); }
    }

    async function criarRestaurante() {
      try {
        const dados = await requisicao(`${API}/restaurantes`, { method: 'POST', body: JSON.stringify({ nome: restauranteNome.value, categoria: restauranteCategoria.value }) });
        mostrarResultado(dados); listarRestaurantes();
      } catch (erro) { mostrarResultado(erro); }
    }
    async function listarRestaurantes() {
      try { mostrarLista(await requisicao(`${API}/restaurantes`), 'restaurantes'); } catch (erro) { mostrarResultado(erro); }
    }
    function preencherRestaurante(item) { restauranteId.value = item.id; restauranteNome.value = item.nome; restauranteCategoria.value = item.categoria; }
    async function atualizarRestaurante() {
      try {
        if (!restauranteId.value) return mostrarResultado({ erro: 'Clique em Editar em algum restaurante antes de atualizar.' });
        const dados = await requisicao(`${API}/restaurantes/${restauranteId.value}`, { method: 'PUT', body: JSON.stringify({ nome: restauranteNome.value, categoria: restauranteCategoria.value }) });
        mostrarResultado(dados); listarRestaurantes();
      } catch (erro) { mostrarResultado(erro); }
    }

    async function criarProduto() {
      try {
        const dados = await requisicao(`${API}/produtos`, { method: 'POST', body: JSON.stringify({ nome: produtoNome.value, preco: parseFloat(produtoPreco.value), restauranteId: parseInt(produtoRestauranteId.value) }) });
        mostrarResultado(dados); listarProdutos();
      } catch (erro) { mostrarResultado(erro); }
    }
    async function listarProdutos() {
      try { mostrarLista(await requisicao(`${API}/produtos`), 'produtos'); } catch (erro) { mostrarResultado(erro); }
    }
    function preencherProduto(item) { produtoId.value = item.id; produtoNome.value = item.nome; produtoPreco.value = item.preco; produtoRestauranteId.value = item.restauranteId; }
    async function atualizarProduto() {
      try {
        if (!produtoId.value) return mostrarResultado({ erro: 'Clique em Editar em algum produto antes de atualizar.' });
        const dados = await requisicao(`${API}/produtos/${produtoId.value}`, { method: 'PUT', body: JSON.stringify({ nome: produtoNome.value, preco: parseFloat(produtoPreco.value), restauranteId: parseInt(produtoRestauranteId.value) }) });
        mostrarResultado(dados); listarProdutos();
      } catch (erro) { mostrarResultado(erro); }
    }

    async function criarPedido() {
      try {
        const cliente = await buscarClientePorNome(pedidoClienteNome.value);
        const produtosIds = pedidoProdutosIds.value.split(',').map(v => parseInt(v.trim())).filter(v => !Number.isNaN(v));
        const dados = await requisicao(`${API}/pedidos`, { method: 'POST', body: JSON.stringify({ clienteId: cliente.id, status: pedidoStatus.value, produtosIds }) });
        mostrarResultado(dados); listarPedidos();
      } catch (erro) { mostrarResultado(erro); }
    }
    async function listarPedidos() {
      try {
        const dados = await requisicao(`${API}/pedidos`);
        console.log("PEDIDOS RECEBIDOS:", dados);
        mostrarResultado(dados);
        mostrarLista(dados, 'pedidos');
      } catch (erro) {
        mostrarResultado(erro);
      }
    }
    function preencherPedido(item) {
      pedidoId.value = item.id; pedidoClienteNome.value = item.clienteNome; pedidoStatus.value = item.status; pedidoProdutosIds.value = '';
      mostrarResultado({ aviso: 'Digite novamente os IDs dos produtos antes de atualizar o pedido.', pedidoSelecionado: item });
    }
    async function atualizarPedido() {
      try {
        if (!pedidoId.value) return mostrarResultado({ erro: 'Clique em Editar em algum pedido antes de atualizar.' });
        const cliente = await buscarClientePorNome(pedidoClienteNome.value);
        const produtosIds = pedidoProdutosIds.value.split(',').map(v => parseInt(v.trim())).filter(v => !Number.isNaN(v));
        const dados = await requisicao(`${API}/pedidos/${pedidoId.value}`, { method: 'PUT', body: JSON.stringify({ clienteId: cliente.id, status: pedidoStatus.value, produtosIds }) });
        mostrarResultado(dados); listarPedidos();
      } catch (erro) { mostrarResultado(erro); }
    }
  
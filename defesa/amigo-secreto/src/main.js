const $ = (selector) => document.querySelector(selector);
const formConsulta = $('#form-consulta');
const formCadastro = $('#form-cadastro');
const lista = $('#lista-popup');
const vazio = $('#vazio');
const contador = $('#contador');
const mensagem = $('#mensagem');
const listaDialog = $('#modal-lista');
const cadastroDialog = $('#modal-cadastro');
const resultDialog = $('#modal-resultado');
let pessoas = [];

async function requisicao(caminho, dados) {
  const resposta = await fetch(`http://localhost:8080/api/${caminho}`, dados ? {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
    body: new URLSearchParams(dados),
  } : {});
  const resultado = await resposta.json();
  if (!resposta.ok) throw new Error(resultado.erro || 'Não foi possível concluir a ação.');
  return resultado;
}

function renderizar() {
  lista.replaceChildren();
  pessoas.forEach((pessoa) => {
    const item = document.createElement('li');
    item.className = 'participant';
    const avatar = document.createElement('span');
    avatar.className = 'avatar';
    avatar.textContent = pessoa.nome.trim().charAt(0).toLocaleUpperCase('pt-BR');
    avatar.setAttribute('aria-hidden', 'true');
    const nomePessoa = document.createElement('strong');
    nomePessoa.className = 'person-name';
    nomePessoa.textContent = pessoa.nome;
    item.append(avatar, nomePessoa);
    lista.append(item);
  });
  contador.textContent = pessoas.length;
  vazio.hidden = pessoas.length > 0;
}

$('#abrir-lista').addEventListener('click', () => listaDialog.showModal());
$('#fechar-lista').addEventListener('click', () => listaDialog.close());
$('#abrir-cadastro').addEventListener('click', () => {
  mensagem.textContent = '';
  formCadastro.reset();
  cadastroDialog.showModal();
});
$('#fechar-cadastro').addEventListener('click', () => cadastroDialog.close());

formCadastro.addEventListener('submit', async (evento) => {
  evento.preventDefault();
  try {
    await requisicao('participantes', {
      nome: $('#nome').value.trim(), email: $('#email').value.trim(),
      desejo1: $('#desejo-1').value.trim(), desejo2: $('#desejo-2').value.trim(), desejo3: $('#desejo-3').value.trim(),
    });
    cadastroDialog.close();
    await carregarParticipantes();
  } catch (erro) {
    mensagem.textContent = erro.message;
  }
});

formConsulta.addEventListener('submit', async (evento) => {
  evento.preventDefault();
  mensagem.textContent = '';
  try {
    const resultado = await requisicao('resultado', { nome: $('#nome-consulta').value.trim(), email: $('#email-consulta').value.trim() });
    mostrarResultado(resultado.amigo, resultado.desejos, $('#email-consulta').value.trim(), resultado.comprado);
  } catch (erro) {
    mensagem.textContent = erro.message;
  }
});

function mostrarResultado(nomeAmigo, desejos, emailConsulta, jaComprado) {
  const conteudo = $('#resultado');
  conteudo.replaceChildren();
  const chamada = document.createElement('p'); chamada.textContent = 'Você tirou';
  const nomeElemento = document.createElement('h2'); nomeElemento.textContent = `${nomeAmigo}!`;
  conteudo.append(chamada, nomeElemento);
  const desejosPreenchidos = desejos.filter(Boolean);
  if (desejosPreenchidos.length) {
    const titulo = document.createElement('p'); titulo.textContent = 'Sugestões de presente'; conteudo.append(titulo);
    const ul = document.createElement('ul'); ul.className = 'gift-list';
    desejosPreenchidos.forEach((desejo) => { const li = document.createElement('li'); li.textContent = desejo; ul.append(li); });
    conteudo.append(ul);
  } else {
    const semDesejos = document.createElement('p'); semDesejos.textContent = 'Ainda não há sugestões de presente.'; conteudo.append(semDesejos);
  }
  const comprado = document.createElement('label'); comprado.className = 'bought-label';
  const checkbox = document.createElement('input'); checkbox.type = 'checkbox'; checkbox.checked = jaComprado;
  checkbox.addEventListener('change', async () => {
    try { await requisicao('presente', { email: emailConsulta, comprado: String(checkbox.checked) }); }
    catch (erro) { mensagem.textContent = erro.message; checkbox.checked = !checkbox.checked; }
  });
  comprado.append(checkbox, document.createTextNode(' Já comprei o presente'));
  conteudo.append(comprado);
  resultDialog.showModal();
}

$('#fechar-resultado').addEventListener('click', () => resultDialog.close());

async function carregarParticipantes() {
  try { pessoas = await requisicao('participantes'); renderizar(); }
  catch (erro) { mensagem.textContent = 'Não foi possível conectar ao back-end Java. Inicie Backend.java.'; }
}

carregarParticipantes();

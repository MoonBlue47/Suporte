/* ============================================================
   SENAI SUPORTE — LocalStorage Data Manager & App Logic
   ============================================================ */

const STORAGE_KEY_TICKETS = 'senai_suporte_tickets';
const STORAGE_KEY_USER = 'senai_suporte_user';

// Mock Seed Data
const MOCK_TICKETS = [
  {
    id: 101,
    nif: '1029384',
    nomeSolicitante: 'Carlos Eduardo Silva',
    numeroSala: 'A-102',
    codigoPatrimonio: 'PAT-9921',
    tipoProblema: 'INFORMATICA',
    descricaoProblema: 'Computador da bancada 3 não liga após pico de energia na sala.',
    status: 'PENDENTE',
    tecnicoResponsavel: '-',
    observacoes: '-',
    dataCriacao: '22/09/2026 08:30'
  },
  {
    id: 102,
    nif: '2049182',
    nomeSolicitante: 'Mariana Oliveira',
    numeroSala: 'B-205',
    codigoPatrimonio: 'PAT-4410',
    tipoProblema: 'ELETRICA',
    descricaoProblema: 'Tomada da bancada principal com mau contato e faíscas ao plugar equipamento.',
    status: 'EM_ANDAMENTO',
    tecnicoResponsavel: 'João Pedro',
    observacoes: 'Disjuntor secundário desligado para substituição da tomada.',
    dataCriacao: '22/09/2026 09:15'
  },
  {
    id: 103,
    nif: '3019283',
    nomeSolicitante: 'Prof. Roberto Santos',
    numeroSala: 'Lab C-12',
    codigoPatrimonio: 'PAT-0012',
    tipoProblema: 'ZELADORIA',
    descricaoProblema: 'Ar condicionado apresentando vazamento de água sobre as mesas de estudo.',
    status: 'CONCLUIDO',
    tecnicoResponsavel: 'Ana Paula',
    observacoes: 'Limpeza do dreno efetuada e teste de gotejamento OK.',
    dataCriacao: '21/09/2026 14:00'
  },
  {
    id: 104,
    nif: '4012948',
    nomeSolicitante: 'Amanda Ferreira',
    numeroSala: 'A-108',
    codigoPatrimonio: 'PAT-8812',
    tipoProblema: 'INFORMATICA',
    descricaoProblema: 'Impressora 3D desconectando da rede local no meio dos trabalhos.',
    status: 'PENDENTE',
    tecnicoResponsavel: '-',
    observacoes: '-',
    dataCriacao: '22/09/2026 10:45'
  },
  {
    id: 105,
    nif: '5928172',
    nomeSolicitante: 'Ricardo Mendes',
    numeroSala: 'Auditório Principal',
    codigoPatrimonio: 'PAT-3329',
    tipoProblema: 'ELETRICA',
    descricaoProblema: 'Luminárias LED do palco oscilando e piscando durante as palestras.',
    status: 'PENDENTE',
    tecnicoResponsavel: '-',
    observacoes: '-',
    dataCriacao: '22/09/2026 11:20'
  }
];

// Data Store Helpers
function getTickets() {
  const data = localStorage.getItem(STORAGE_KEY_TICKETS);
  if (!data) {
    saveTickets(MOCK_TICKETS);
    return MOCK_TICKETS;
  }
  try {
    return JSON.parse(data);
  } catch (e) {
    saveTickets(MOCK_TICKETS);
    return MOCK_TICKETS;
  }
}

function saveTickets(tickets) {
  localStorage.setItem(STORAGE_KEY_TICKETS, JSON.stringify(tickets));
}

function getTicketById(id) {
  const tickets = getTickets();
  return tickets.find(t => String(t.id) === String(id));
}

function addTicket(ticketData) {
  const tickets = getTickets();
  const nextId = tickets.length > 0 ? Math.max(...tickets.map(t => t.id)) + 1 : 101;
  const now = new Date();
  const formattedDate = `${String(now.getDate()).padStart(2, '0')}/${String(now.getMonth() + 1).padStart(2, '0')}/${now.getFullYear()} ${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;

  const newTicket = {
    id: nextId,
    nif: ticketData.nif,
    nomeSolicitante: ticketData.nomeSolicitante,
    numeroSala: ticketData.numeroSala,
    codigoPatrimonio: ticketData.codigoPatrimonio,
    tipoProblema: ticketData.tipoProblema,
    descricaoProblema: ticketData.descricaoProblema,
    status: 'PENDENTE',
    tecnicoResponsavel: '-',
    observacoes: '-',
    dataCriacao: formattedDate
  };

  tickets.unshift(newTicket);
  saveTickets(tickets);
  return newTicket;
}

function updateTicket(id, updatedFields) {
  const tickets = getTickets();
  const index = tickets.findIndex(t => String(t.id) === String(id));
  if (index !== -1) {
    tickets[index] = { ...tickets[index], ...updatedFields };
    saveTickets(tickets);
    return tickets[index];
  }
  return null;
}

function deleteTicket(id) {
  let tickets = getTickets();
  tickets = tickets.filter(t => String(t.id) !== String(id));
  saveTickets(tickets);
}

// Auth Helpers
function getCurrentUser() {
  const data = localStorage.getItem(STORAGE_KEY_USER);
  return data ? JSON.parse(data) : null;
}

function setCurrentUser(user) {
  localStorage.setItem(STORAGE_KEY_USER, JSON.stringify(user));
}

function logoutUser() {
  localStorage.removeItem(STORAGE_KEY_USER);
  window.location.href = 'index.html';
}

// Toast Helper
function showToast(message, type = 'success') {
  let container = document.querySelector('.toast-container');
  if (!container) {
    container = document.createElement('div');
    container.className = 'toast-container';
    container.style.cssText = `
      position: fixed;
      top: 24px;
      right: 24px;
      z-index: 9999;
      display: flex;
      flex-direction: column;
      gap: 10px;
    `;
    document.body.appendChild(container);
  }

  const toast = document.createElement('div');
  const bgColor = type === 'success' ? '#10B981' : type === 'danger' ? '#C71811' : '#00A5D4';
  toast.style.cssText = `
    background: ${bgColor};
    color: white;
    padding: 12px 20px;
    border-radius: 8px;
    font-weight: 500;
    font-size: 0.9rem;
    box-shadow: 0 4px 14px rgba(0,0,0,0.15);
    display: flex;
    align-items: center;
    gap: 8px;
    animation: fadeInRight 0.3s cubic-bezier(0.16, 1, 0.3, 1);
  `;
  toast.innerHTML = message;
  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateX(20px)';
    toast.style.transition = 'all 0.3s ease';
    setTimeout(() => toast.remove(), 300);
  }, 4000);
}

// Helpers for format badges
function getStatusBadge(status) {
  switch (status) {
    case 'PENDENTE':
      return `<span class="badge badge-pending"><i class="ph ph-clock"></i> Pendente</span>`;
    case 'EM_ANDAMENTO':
      return `<span class="badge badge-inprogress"><i class="ph ph-gear-six spin"></i> Em Andamento</span>`;
    case 'CONCLUIDO':
      return `<span class="badge badge-done"><i class="ph ph-check-circle"></i> Concluído</span>`;
    default:
      return `<span class="badge">${status}</span>`;
  }
}

function getTipoBadge(tipo) {
  switch (tipo) {
    case 'INFORMATICA':
      return `<span class="badge badge-tipo-info"><i class="ph ph-desktop"></i> Informática</span>`;
    case 'ELETRICA':
      return `<span class="badge badge-tipo-elet"><i class="ph ph-lightning"></i> Elétrica</span>`;
    case 'ZELADORIA':
      return `<span class="badge badge-tipo-zel"><i class="ph ph-broom"></i> Zeladoria</span>`;
    default:
      return `<span class="badge">${tipo}</span>`;
  }
}

// Execute logic when DOM loads
document.addEventListener('DOMContentLoaded', () => {
  // Global check for logged user badge in header
  const user = getCurrentUser();
  const navUserEl = document.getElementById('navUserSection');
  if (navUserEl) {
    if (user) {
      navUserEl.innerHTML = `
        <div style="display:flex; align-items:center; gap:12px;">
          <span style="color:var(--gray-300); font-size:var(--text-sm);">
            Olá, <strong style="color:var(--white);">${user.nome}</strong>
          </span>
          <a href="painel.html" class="btn btn-outline-light btn-sm">Painel</a>
          <button onclick="logoutUser()" class="btn btn-icon btn-sm" title="Sair" style="color:var(--gray-300); background:none; border:none; cursor:pointer;">
            <i class="ph ph-sign-out" style="font-size:1.2rem; color:var(--red-light);"></i>
          </button>
        </div>
      `;
    } else {
      navUserEl.innerHTML = `
        <a href="login.html" class="btn btn-outline-light btn-sm">Área do Técnico</a>
      `;
    }
  }
});

/* ============================================================
   SENAI SUPORTE — Main JavaScript
   ============================================================ */

document.addEventListener('DOMContentLoaded', () => {

  // ── 1. Auto-dismiss flash alerts ──────────────────────────
  const alerts = document.querySelectorAll('.alert[data-auto-dismiss]');
  alerts.forEach(alert => {
    const delay = parseInt(alert.dataset.autoDismiss || '5000', 10);
    setTimeout(() => fadeOutAlert(alert), delay);
  });

  // Close button on alerts
  document.querySelectorAll('.alert-close').forEach(btn => {
    btn.addEventListener('click', () => fadeOutAlert(btn.closest('.alert')));
  });

  function fadeOutAlert(el) {
    if (!el) return;
    el.style.transition = 'opacity .4s ease, transform .4s ease';
    el.style.opacity = '0';
    el.style.transform = 'translateY(-8px)';
    setTimeout(() => el.remove(), 420);
  }

  // ── 2. Form input animations ──────────────────────────────
  document.querySelectorAll('.form-control').forEach(input => {
    const group = input.closest('.form-group');
    if (!group) return;

    input.addEventListener('focus', () => {
      group.classList.add('focused');
    });
    input.addEventListener('blur', () => {
      group.classList.remove('focused');
      if (input.value.trim()) group.classList.add('has-value');
      else group.classList.remove('has-value');
    });
  });

  // ── 3. Submit button loading state ───────────────────────
  document.querySelectorAll('form').forEach(form => {
    form.addEventListener('submit', (e) => {
      const btn = form.querySelector('button[type="submit"], .btn-submit');
      if (!btn) return;

      // Skip if form has validation errors
      if (!form.checkValidity()) return;

      const originalText = btn.innerHTML;
      btn.disabled = true;
      btn.innerHTML = `<svg class="spin" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="3" stroke-dasharray="31.4 62.8" stroke-linecap="round"/></svg> Aguarde...`;

      // Re-enable after 8s as fallback
      setTimeout(() => {
        btn.disabled = false;
        btn.innerHTML = originalText;
      }, 8000);
    });
  });

  // ── 4. Table row click → navigate ────────────────────────
  document.querySelectorAll('tr[data-href]').forEach(row => {
    row.style.cursor = 'pointer';
    row.addEventListener('click', (e) => {
      if (e.target.closest('.btn, a, button, input, select')) return;
      window.location.href = row.dataset.href;
    });
  });

  // ── 5. Tooltip init (title attr) ─────────────────────────
  document.querySelectorAll('[data-tooltip]').forEach(el => {
    const tip = document.createElement('div');
    tip.className = 'tooltip-bubble';
    tip.textContent = el.dataset.tooltip;
    document.body.appendChild(tip);

    el.addEventListener('mouseenter', (e) => {
      const rect = el.getBoundingClientRect();
      tip.style.cssText = `
        position:fixed;
        left:${rect.left + rect.width/2}px;
        top:${rect.top - 8}px;
        transform:translateX(-50%) translateY(-100%);
        background:${getComputedStyle(document.documentElement).getPropertyValue('--gray-800')};
        color:#fff;
        padding:.35rem .7rem;
        border-radius:6px;
        font-size:.75rem;
        font-weight:600;
        white-space:nowrap;
        z-index:9999;
        pointer-events:none;
        opacity:1;
        transition:opacity .15s;
      `;
    });

    el.addEventListener('mouseleave', () => {
      tip.style.opacity = '0';
    });
  });

  // ── 6. Stats counter animation ───────────────────────────
  document.querySelectorAll('.stat-value[data-count]').forEach(el => {
    const target = parseInt(el.dataset.count, 10);
    if (isNaN(target)) return;

    let start = 0;
    const duration = 800;
    const startTime = performance.now();

    function updateCount(currentTime) {
      const elapsed = currentTime - startTime;
      const progress = Math.min(elapsed / duration, 1);
      const eased = 1 - Math.pow(1 - progress, 3);
      const current = Math.round(eased * target);
      el.textContent = current;
      if (progress < 1) requestAnimationFrame(updateCount);
    }

    // Trigger when in viewport
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          requestAnimationFrame(updateCount);
          observer.unobserve(el);
        }
      });
    });
    observer.observe(el);
  });

  // ── 7. Navbar active link ─────────────────────────────────
  const currentPath = window.location.pathname;
  document.querySelectorAll('.navbar-nav a').forEach(link => {
    if (link.getAttribute('href') === currentPath) {
      link.classList.add('active');
    }
  });

  // ── 8. Animate page elements on load ──────────────────────
  const animatables = document.querySelectorAll('.card, .stat-card, .feature-card');
  const observerOpts = { threshold: 0.1 };
  const fadeObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry, i) => {
      if (entry.isIntersecting) {
        setTimeout(() => {
          entry.target.style.opacity = '1';
          entry.target.style.transform = 'translateY(0)';
        }, i * 60);
        fadeObserver.unobserve(entry.target);
      }
    });
  }, observerOpts);

  animatables.forEach(el => {
    el.style.opacity = '0';
    el.style.transform = 'translateY(12px)';
    el.style.transition = 'opacity .4s ease, transform .4s ease';
    fadeObserver.observe(el);
  });

  // ── 9. Confirm dangerous actions ──────────────────────────
  document.querySelectorAll('[data-confirm]').forEach(el => {
    el.addEventListener('click', (e) => {
      const message = el.dataset.confirm || 'Tem certeza?';
      if (!confirm(message)) {
        e.preventDefault();
        e.stopPropagation();
      }
    });
  });

});

// ── Spin animation (CSS injected by JS) ──────────────────────
const spinStyle = document.createElement('style');
spinStyle.textContent = `
  @keyframes spin { to { transform: rotate(360deg); } }
  .spin { animation: spin .7s linear infinite; display:inline-block; vertical-align:middle; }
`;
document.head.appendChild(spinStyle);

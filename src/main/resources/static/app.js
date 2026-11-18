// Brauzerin yaddaşından giriş etmiş istifadəçini oxuyur
function getUser() {
    const raw = localStorage.getItem('user');
    return raw ? JSON.parse(raw) : null;
}

function saveUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = 'index.html';
}

// Backend ilə danışan ortaq funksiya
async function api(url, method = 'GET', body = null) {
    const options = { method: method, headers: { 'Content-Type': 'application/json' } };
    if (body) {
        options.body = JSON.stringify(body);
    }
    const response = await fetch(url, options);

    let data = null;
    try {
        data = await response.json();
    } catch (e) {
        // cavabda məlumat yoxdursa, davam edirik
    }

    if (!response.ok) {
        throw new Error(data && data.message ? data.message : 'Xəta baş verdi');
    }
    return data;
}

// Mətndəki təhlükəli simvolları təmizləyir
function esc(text) {
    if (text === null || text === undefined) return '';
    return String(text)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;');
}

// Ulduz mətni (rəylərdə istifadə olunur)
function starsHtml(rating) {
    const full = Math.round(rating);
    return '★'.repeat(full) + '☆'.repeat(5 - full);
}

// Ruletka xətti: 4.5 reytinq beş bölmənin 4.5-nin dolması deməkdir
function tapeBar(rating) {
    const value = Math.max(0, Math.min(5, rating));
    const percent = value / 5 * 100;
    return `<div class="tape-bar" style="--v:${percent}%" role="img" aria-label="Reytinq: ${value} / 5"><i></i></div>`;
}

const statusText = {
    PENDING: 'Gözləyir',
    ACCEPTED: 'Qəbul edildi',
    COMPLETED: 'Tamamlandı'
};

// Yuxarı menyunu çəkir
function renderNav() {
    const nav = document.getElementById('nav');
    if (!nav) return;

    const user = getUser();
    let right;
    if (user) {
        right = `<a href="orders.html">Sifarişlərim</a>
                 <span class="user-name">${esc(user.fullName)}</span>
                 <a href="#" onclick="logout(); return false;">Çıxış</a>`;
    } else {
        right = `<a class="nav-cta" href="auth.html">Giriş və qeydiyyat</a>`;
    }

    nav.innerHTML = `<a class="logo" href="index.html"><span class="mark"></span>UstaTap</a>
                     <div class="nav-links"><a href="index.html">Ustalar</a>${right}</div>`;
}

document.addEventListener('DOMContentLoaded', renderNav);
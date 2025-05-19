let chats = JSON.parse(localStorage.getItem('chats') || '{}');
let currentUser = Object.keys(chats)[0] || null;

const contacts = document.querySelectorAll('.contact');
const messagesContainer = document.getElementById('messages');
const chatTitle = document.querySelector('.chat-title');

// Загрузка сообщений для выбранного пользователя
function loadMessages(user) {
  messagesContainer.innerHTML = '';
  if (!chats[user]) return;

  chats[user].forEach(msg => {
    const div = document.createElement('div');
    div.classList.add('message', msg.from === 'me' ? 'from-me' : 'from-them');
    div.textContent = msg.text;
    messagesContainer.appendChild(div);
  });
  messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

// Отправка сообщения
function sendMessage() {
  const input = document.getElementById("chat-text");
  const text = input.value.trim();
  if (text === "") return;

  const div = document.createElement("div");
  div.classList.add("message", "from-me");
  div.textContent = text;
  messagesContainer.appendChild(div);
  messagesContainer.scrollTop = messagesContainer.scrollHeight;

  if (!chats[currentUser]) chats[currentUser] = [];
  chats[currentUser].push({ from: 'me', text });

  localStorage.setItem('chats', JSON.stringify(chats));

  input.value = "";
}

// Инициализация слушателей контактов
contacts.forEach(contact => {
  contact.addEventListener('click', () => {
    contacts.forEach(c => c.classList.remove('active'));
    contact.classList.add('active');
    const user = contact.dataset.user;
    currentUser = user;
    chatTitle.textContent = user;
    loadMessages(user);
  });
});

// Восстановление контактов из localStorage
function restoreContacts() {
  const contactList = document.getElementById('contact-list');

  Object.keys(chats).forEach(user => {
    if (!document.querySelector(`.contact[data-user="${user}"]`)) {
      const newContact = document.createElement('div');
      newContact.classList.add('contact');
      newContact.dataset.user = user;
      newContact.textContent = user;
      newContact.addEventListener('click', () => {
        document.querySelectorAll('.contact').forEach(c => c.classList.remove('active'));
        newContact.classList.add('active');
        currentUser = user;
        chatTitle.textContent = user;
        loadMessages(user);
      });
      contactList.appendChild(newContact);
    }
  });
}

// Создание нового чата по номеру телефона
function createChatByPhone() {
  const phone = prompt("Введите номер телефона пользователя:");
  if (!phone) return;

  const users = JSON.parse(localStorage.getItem('users') || '[]');
  const user = users.find(u => u.phone === phone);
  if (!user) {
    alert("Пользователь не найден.");
    return;
  }

  const userName = `${user.FirstName} ${user.LastName}`;
  if (!chats[userName]) {
    chats[userName] = [];
    localStorage.setItem('chats', JSON.stringify(chats));
    const contactList = document.getElementById('contact-list');
    const newContact = document.createElement('div');
    newContact.classList.add('contact');
    newContact.dataset.user = userName;
    newContact.textContent = userName;
    newContact.addEventListener('click', () => {
      document.querySelectorAll('.contact').forEach(c => c.classList.remove('active'));
      newContact.classList.add('active');
      currentUser = userName;
      chatTitle.textContent = userName;
      loadMessages(userName);
    });
    contactList.appendChild(newContact);
    currentUser = userName;
    chatTitle.textContent = userName;
    loadMessages(userName);
  } else {
    alert("Чат с этим пользователем уже существует.");
  }
}

// Открытие профиля текущего пользователя
function openProfile() {
  const user = JSON.parse(localStorage.getItem('currentUser')) || {};

  document.getElementById('profile-LastName').textContent = user.LastName || '—';
  document.getElementById('profile-FirstName').textContent = user.FirstName || '—';
  document.getElementById('profile-Patronymic').textContent = user.Patronymic || '—';
  document.getElementById('profile-email').textContent = user.email || '—';
  document.getElementById('profile-phone').textContent = user.phone || '—';

  const photo = document.getElementById('profile-photo');
  photo.src = user.photo || '/icons/user-icon.png';

  document.getElementById('profile-modal').style.display = 'flex';
}

function closeProfile() {
  document.getElementById('profile-modal').style.display = 'none';
}

// Обработка Enter для отправки
document.getElementById('chat-text').addEventListener('keypress', function (e) {
  if (e.key === 'Enter') sendMessage();
});

// Инициализация
if (currentUser) {
  loadMessages(currentUser);
  chatTitle.textContent = currentUser;
}
restoreContacts();

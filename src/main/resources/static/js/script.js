let currentLoginMethod = 'phone';

// Показать форму регистрации
function showRegisterForm() {
  document.getElementById('auth-container').style.display = 'none';
  document.getElementById('register-container').style.display = 'block';
}

// Показать форму авторизации
function showLoginForm() {
  document.getElementById('register-container').style.display = 'none';
  document.getElementById('auth-container').style.display = 'block';
}

function switchLoginMethod(method) {
  currentLoginMethod = method;
  const fields = document.getElementById('login-fields');
  const btnPhone = document.getElementById('btn-phone');
  const btnEmail = document.getElementById('btn-email');


  if (method === 'phone') {
    fields.innerHTML = '<input type="text" id="login-phone" placeholder="Phone Number" />';
    btnPhone.classList.add('active-method');
    btnEmail.classList.remove('active-method');
  } else {
    fields.innerHTML = '<input type="email" id="login-email" placeholder="Email Address" />';
    btnEmail.classList.add('active-method');
    btnPhone.classList.remove('active-method');
  }
}

function sendLoginCode() {
  const value = currentLoginMethod === 'phone'
    ? document.getElementById('login-phone').value
    : document.getElementById('login-email').value;
  
  const password = document.getElementById('login-password').value;
  
  if (!value || !password) {
    alert('Please enter your ' + currentLoginMethod + ' and password');
    return;
  }

  // Моделируем отправку кода
  document.getElementById('2fa-section').style.display = 'block';
  alert('Code sent to ' + value);
}

function verifyCode() {
  const code = document.getElementById('code-input').value;
  if (code === '123456') {
    alert('2FA success!');
    document.getElementById('auth-container').style.display = 'none';
    document.getElementById('messenger-container').style.display = 'block';
  } else {
    alert('Invalid code!');
  }
}
  //Регистрация и проверки
function register() {
  const name = document.getElementById('reg-name').value;
  const phone = document.getElementById('reg-phone').value;
  const email = document.getElementById('reg-email').value;
  const password = document.getElementById('reg-password').value;
  const confirmPassword = document.getElementById('reg-password-confirm').value;

  if (!name || !phone || !email || !password || !confirmPassword) return alert('Заполните все поля');

  const isEmail = /^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/.test(email);
  const isPhone = /^[\d\+][\d\(\)\ -]{4,14}\d$/.test(phone);

  if (!isEmail && !isPhone) {
    return alert("Введите корректный email или номер телефона");
  }

  if (password !== confirmPassword) {
    alert('Пароли не совпадают');
    return;
  }

  alert('Registered: ' + name);
  // Тут можно отправить данные на сервер
}

  //Отправка сообщений
function sendMessage() {
  const input = document.getElementById('chat-input');
  const message = input.value;
  if (!message) return;
  
  const messages = document.getElementById('chat-messages');
  const div = document.createElement('div');
  div.textContent = message;
  messages.appendChild(div);
  input.value = '';
}

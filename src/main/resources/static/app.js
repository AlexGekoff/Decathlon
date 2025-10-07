document.addEventListener('DOMContentLoaded', () => {

  const el = id => document.getElementById(id);
  const err = el('error');
  const msg = el('msg');

  const decathlonEvents = [
    "Dec_100m","Dec_400m","Dec_1500m","Dec_110mHurdles",
    "Dec_LongJump","Dec_HighJump","Dec_PoleVault","Dec_DiscusThrow",
    "Dec_JavelinThrow","Dec_ShotPut"
  ];

  const heptathlonEvents = [
    "Hep_100mHurdles","Hep_200m","Hep_800m","Hep_HighJump",
    "Hep_LongJump","Hep_ShotPut","Hep_JavelinThrow"
  ];

  // Kolumnordning i tabellen
  const columnOrder = [
    "Hep_200m","Hep_800m","Hep_100mHurdles","Hep_HighJump",
    "Hep_LongJump","Hep_ShotPut","Hep_JavelinThrow",
    "Dec_1500m","Dec_JavelinThrow","Dec_PoleVault","Dec_DiscusThrow",
    "Dec_110mHurdles","Dec_HighJump","Dec_100m","Dec_LongJump","Dec_ShotPut","Dec_400m"
  ];

  function setError(text) { err.textContent = text; }
  function setMsg(text) { msg.textContent = text; err.textContent = ''; }

  function updateEventOptions(category) {
    const select = el('event');
    select.innerHTML = '<option value="">-- Select Event --</option>';
    const events = category === 'Decathlon' ? decathlonEvents :
                   category === 'Heptathlon' ? heptathlonEvents : [];
    events.forEach(ev => {
      const opt = document.createElement('option');
      opt.value = ev;
      opt.textContent = ev;
      select.appendChild(opt);
    });
  }

  async function renderStandings() {
    try {
      const res = await fetch('/api/standings');
      const data = await res.json();

      if (!data.length) {
        el('standings').innerHTML = '<tr><td colspan="2">No competitors yet</td></tr>';
        return;
      }

      // 1️⃣ Собираем все дисциплины, по которым есть хоть один результат
      const allEvents = new Set();
      data.forEach(c => {
        Object.keys(c.scores || {}).forEach(e => allEvents.add(e));
      });
      const events = Array.from(allEvents);

      // 2️⃣ Строим заголовок таблицы динамически
      const headerRow = `
        <tr>
          <th>Rank</th>
          <th>Name</th>
          ${events.map(e => `<th>${e}</th>`).join('')}
          <th>Total</th>
        </tr>
      `;
      el('standings').previousElementSibling.innerHTML = headerRow; // обновляем <thead>

      // 3️⃣ Строим строки с данными
      const rows = data.map((c, i) => `
        <tr>
          <td>${i + 1}</td>
          <td>${c.name}</td>
          ${events.map(e => `<td>${c.scores?.[e] ?? ''}</td>`).join('')}
          <td>${c.total ?? 0}</td>
        </tr>
      `).join('');

      el('standings').innerHTML = rows;

    } catch (e) {
      setError('Failed to load standings');
    }
  }


  // Event Listeners
  el('category').addEventListener('change', () => updateEventOptions(el('category').value));



  el('save').addEventListener('click', async () => {
    const name = el('name2').value.trim();
    const event = el('event').value;
    const raw = parseFloat(el('raw').value);

    if (!name || !event || isNaN(raw)) {
      setError('Enter name, select event and input a valid result');
      return;
    }

    try {
      const res = await fetch('/api/score', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, event, raw })
      });
      if (!res.ok) {
        const t = await res.text();
        setError(t || 'Failed to save score');
        return;
      }

      const competitor = await res.json();
      setMsg(`Saved score for ${competitor.name} in ${event}: ${competitor.scores[event]} pts`);
      el('name2').value = '';
      el('raw').value = '';
      el('event').value = '';
      el('category').value = '';
      await renderStandings();

    } catch (e) {
      setError('Network error while saving score');
    }
  });

  // Initial render
  updateEventOptions('');
  renderStandings();

});

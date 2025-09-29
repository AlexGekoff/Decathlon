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

      const rows = data.map(c => `
        <tr>
          <td>${c.name}</td>
          ${columnOrder.map(ev => `<td>${c.scores?.[ev] ?? ''}</td>`).join('')}
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

  el('add').addEventListener('click', async () => {
    const name = el('name').value.trim();
    if (!name) { setError('Enter a name'); return; }

    try {
      const res = await fetch('/api/competitors', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name })
      });
      if (!res.ok) {
        const t = await res.text();
        setError(t || 'Failed to add competitor');
      } else {
        setMsg(`Competitor "${name}" added`);
        el('name').value = '';
      }
      await renderStandings();
    } catch (e) {
      setError('Network error');
    }
  });

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

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

  async function fetchStandings() {
    const res = await fetch('/api/standings');
    if (!res.ok) throw new Error('failed');
    return res.json();
  }

  async function renderStandings() {
    try {
      const data = await fetchStandings();

      if (!data.length) {
        el('standings').innerHTML = '<tr><td colspan="2">No competitors yet</td></tr>';
        el('standings').previousElementSibling.innerHTML = '';
        return;
      }

      const allEvents = new Set();
      data.forEach(c => {
        Object.keys(c.scores || {}).forEach(e => allEvents.add(e));
      });

      const ordered = [...decathlonEvents, ...heptathlonEvents].filter(e => allEvents.has(e));
      const events = ordered.length ? ordered : Array.from(allEvents);

      const headerRow = `
        <tr>
          <th>Rank</th>
          <th>Name</th>
          ${events.map(e => `<th>${e}</th>`).join('')}
          <th>Total</th>
        </tr>
      `;
      el('standings').previousElementSibling.innerHTML = headerRow;

      const sorted = [...data].sort((a,b) => (b.total||0) - (a.total||0));
      const rows = sorted.map((c, i) => `
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

  function toCSV(rows) {
    return rows.map(r => r.map(v => {
      const s = v == null ? '' : String(v);
      if (s.includes(',') || s.includes('"') || s.includes('\n')) {
        return `"${s.replace(/"/g, '""')}"`;
      }
      return s;
    }).join(',')).join('\n');
  }

  async function exportCSV() {
    try {
      const data = await fetchStandings();
      if (!data.length) {
        setError('No data to export');
        return;
      }

      const present = new Set();
      data.forEach(c => Object.keys(c.scores || {}).forEach(e => present.add(e)));

      const orderedEvents = [...decathlonEvents, ...heptathlonEvents].filter(e => present.has(e));
      const usedEvents = orderedEvents;

      const sorted = [...data].sort((a,b) => (b.total||0) - (a.total||0));

      const header = ["Rank","Name",...usedEvents,"Total Score"];
      const rows = [header];

      let rank = 1;
      for (const c of sorted) {
        const r = [rank, c.name];
        for (const ev of usedEvents) {
          r.push(c.scores && ev in c.scores ? c.scores[ev] : '');
        }
        r.push(c.total ?? 0);
        rows.push(r);
        rank++;
      }

      const csv = toCSV(rows);
      const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'standings.csv';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      URL.revokeObjectURL(url);
      setMsg('Exported standings');
    } catch (e) {
      setError('Export failed');
    }
  }

  el('category').addEventListener('change', () => updateEventOptions(el('category').value));

  el('save').addEventListener('click', async () => {
    const name = el('name2').value.trim();
    const event = el('event').value;
    const raw = parseFloat(el('raw').value);

    const nameRegex = /^[A-Za-zÅÄÖåäö\s]+$/;
    if (!nameRegex.test(name)) {
      setError('Invalid input – you can only enter letters');
      return;
    }

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

  el('export').addEventListener('click', exportCSV);

  updateEventOptions('');
  renderStandings();

});

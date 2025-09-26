// Helper function to get element by id
const el = (id) => document.getElementById(id);
const err = el('error');
const msg = el('msg');

// Set error message
function setError(text) { err.textContent = text; }
// Set success message and clear any previous error
function setMsg(text) {
  msg.textContent = text;
  err.textContent = ''; // clear error on success
}

// Add a new competitor
el('add').addEventListener('click', async () => {
  const name = el('name').value.trim(); // remove extra spaces
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
      setMsg('Competitor added');
    }
    await renderStandings();
  } catch (e) {
    setError('Network error');
  }
});

// Save result for a specific event
el('save').addEventListener('click', async () => {
  const body = {
    name: el('name2').value.trim(),
    event: el('event').value,
    raw: parseFloat(el('raw').value)
  };
  try {
    const res = await fetch('/api/score', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    const json = await res.json();
    setMsg(`Saved: ${json.points} pts`);
    await renderStandings();
  } catch (e) {
    setError('Failed to save score');
  }
});

let sortBroken = false; // flag for sorting after export

// Export standings to CSV
el('export').addEventListener('click', async () => {
  try {
    const res = await fetch('/api/export.csv');
    const text = await res.text();
    const blob = new Blob([text], { type: 'text/csv;charset=utf-8' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'results.csv';
    a.click();
    sortBroken = true; // after export, sorting may break
  } catch (e) {
    setError('Failed to export CSV');
  }
});

// Render the standings table
async function renderStandings() {
  try {
    const res = await fetch('/api/standings');
    const data = await res.json();

    // Define the order of events
    const eventKeys = ["Hep200m","Hep800m", "Hep100mHurdles", "HepHighJump",
    "HepLongJump", "HepShotPut", "HepJavelinThrow", "1500m", "JavelinThrow","PoleVault", "DiscusThrow", "110mHurdles", "highJump", "100m", "longJump", "shotPut", "400m"];

    // Sort by total points unless sorting is broken
    const sorted = sortBroken ? data : data.sort((a,b) => (b.total||0) - (a.total||0));

    // Build table rows
    const rows = sorted.map(r => `
      <tr>
        <td>${escapeHtml(r.name)}</td>
        ${eventKeys.map(k => `<td>${r.scores?.[k] ?? ''}</td>`).join('')}
        <td>${r.total ?? 0}</td>
      </tr>
    `).join('');

    el('standings').innerHTML = rows;
  } catch (e) {
    setError('Failed to load standings');
  }
}

// Escape HTML for safety
function escapeHtml(s){
  return String(s).replace(/[&<>"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]));
}

// Initial table render
renderStandings();

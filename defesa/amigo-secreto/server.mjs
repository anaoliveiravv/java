import { createServer } from 'node:http';
import { createReadStream, existsSync } from 'node:fs';
import { extname, join } from 'node:path';

const root = new URL('.', import.meta.url).pathname;
const files = new Map([
  ['/', 'index.html'],
  ['/src/main.js', 'src/main.js'],
  ['/src/style.css', 'src/style.css'],
]);
const types = { '.html': 'text/html; charset=utf-8', '.js': 'text/javascript; charset=utf-8', '.css': 'text/css; charset=utf-8' };

createServer((req, res) => {
  const path = files.get(new URL(req.url, 'http://localhost').pathname);
  const file = path && join(root, path);
  if (!file || !existsSync(file)) { res.writeHead(404); res.end('Not found'); return; }
  res.writeHead(200, { 'Content-Type': types[extname(file)] });
  createReadStream(file).pipe(res);
}).listen(5173, '127.0.0.1', () => console.log('Front disponível em http://localhost:5173'));

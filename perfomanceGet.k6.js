import http from 'k6/http';
import { sleep, check } from 'k6';

/*
  Test to look how many VUs this API can handle
  INSERT INTO url (original_url, alias, shorten_url) VALUES
  ('https://exemplo.com', '1', 'https://exemplo.com/1'),
  ('https://exemplo.com', '2', 'https://exemplo.com/2'),
  ('https://exemplo.com', '3', 'https://exemplo.com/3');
*/

export let options = {
  stages: [
    { duration: '30s', target: 30 },
    { duration: '1m', target: 100 },
    { duration: '30s', target: 10 },
    { duration: '10s', target: 500 },
    { duration: '20s', target: 2000 },
    { duration: '20s', target: 4000 },
    { duration: '20s', target: 6000 },
    { duration: '20s', target: 8000 },
    { duration: '15s', target: 200 },
    { duration: '30s', target: 15 },
  ],
};

export default function () {
  const params = [
    "https://exemplo.com/1",
    "https://exemplo.com/2",
    "https://exemplo.com/3"
  ];

  const getRandomShortUrl = () => {
    return params[Math.floor(Math.random() * params.length)];
  }

  const url = `http://127.0.0.1:8080/v1/url?shortUrl=${getRandomShortUrl()}`;
  const res = http.get(url);

  check(res, {
    'status code is 200': (r) => r.status === 200,
  });

  sleep(1);
}

import http from 'k6/http';
import { sleep, check } from 'k6';

/*
    Test to see how many VUs this API can handle using 1 POST request and 9 GET request
*/

export let options = {
    stages: [
      { duration: '30s', target: 30 },
      { duration: '1m', target: 100 },
      { duration: '10s', target: 500 },
      { duration: '10s', target: 1000 },
      { duration: '20s', target: 2000 },
      { duration: '30s', target: 4000 },
      { duration: '10s', target: 4500 },
      { duration: '10s', target: 3500 },
      { duration: '15s', target: 1000 },
      { duration: '30s', target: 150 },
    ],
  };

export default function () {
  const baseUrl = 'http://127.0.0.1:8080/v1/url';
  const shortenEndpoint = `${baseUrl}/shorten`;
  const getEndpoint = baseUrl;

  const generateRandomNumber = () => {
    return Math.floor(Math.random() * 1000000);
  }

  const randomAlias = [`exemplo${generateRandomNumber()}`, `gpt${generateRandomNumber()}`, `hello${generateRandomNumber()}`, `world${generateRandomNumber()}`]

  const payload = JSON.stringify({
    url: "https://exemplo.com",
    alias: randomAlias[Math.floor(Math.random() * randomAlias.length)]
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const postResponse = http.post(shortenEndpoint, payload, params);
  check(postResponse, {
    'POST status is 200': (r) => r.status === 200,
  });

  const shortUrl = postResponse.json().shortenUrl;

  // 9 Requisições de Leitura (GET)
  for (let i = 0; i < 9; i++) {
    const getResponse = http.get(`${getEndpoint}?shortUrl=${shortUrl}`);
    check(getResponse, {
      'GET status is 200': (r) => r.status === 200,
    });
    sleep(1);
  }

  sleep(1);
}

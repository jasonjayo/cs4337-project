function checkStatusCode(res) {
  if (res.status === 401) {
    window.location.href = `http://${window.location.hostname}:8080/oauth2/authorization/google`;
  } else {
    return res;
  }
}

export { checkStatusCode };

<template>
  <div id="footer">
    <div class="footerMessage">
      <img src="@/assets/images/MyLogo.png" alt="" />
    </div>
    <div class="footerMessage">
      <a href="#/myhotel/">"MyHotel"</a>
      <p>© 2023-2023 Official site</p>
    </div>
    <div class="footerMessage">
      <p>9 Geiger Rd, Key West, Florida 33040, USA</p>
      <p>Phone number: 267-293-9210</p>
    </div>
    <div class="mySocial">
      <a href="#/"><i class="bi bi-telegram"></i></a>
      <a href="#/"><i class="bi bi-instagram"></i></a>
      <a href="https://github.com/Rezozo"><i class="bi bi-github"></i></a>
      <a href="#/"><i class="bi bi-whatsapp"></i></a>
    </div>
  </div>
</template>

<style scoped>
#footer {
  box-shadow: 0px 0px 4px black;
  width: 100%;
  height: 250px;
  padding-top: 40px;
  background-color: white;
  max-width: 2000px;
  margin: 0 auto;
}
.footerMessage {
  margin-right: 80px;
  display: inline-block;
}

#footer img {
  border-radius: 0%;
  margin-left: 300px;
  width: 150px;
  height: 85px;
}

#footer .mySocial a {
  text-decoration: none;
  color: black;
  font-size: 40px;
}

.footerMessage a {
  color: black;
}

.mySocial {
  margin-top: 20px;
  text-align: center;
}

.mySocial i {
  margin-left: 20px;
}
</style>
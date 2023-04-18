<template>
  <div id="registration">
    <div class="image">
      <img src="@/assets/images/Reg.webp" alt="" />
    </div>

    <div class="form">
      <h2>Registration</h2><br />

      <input type="text" placeholder="Full name" id="fullName" v-model="userInfo.fullname" maxlength="255" /><br />

      <input  type="text" placeholder="Email" id="email" v-model="userInfo.email" maxlength="70" /><br />

      <input type="text" placeholder="Phone Number" id="phoneNumber" v-model="userInfo.phoneNumber" oninput="this.value = this.value.replace(/[^0-9]/g, '')" maxlength="18" /><br />

      <input type="password" placeholder="Password" id="password" v-model="userInfo.password" /><br />

      <span v-if="userInfo.errorMessage" style="color: #8b0000">{{ userInfo.errorMessage}}</span><br v-if="userInfo.errorMessage" />

      <button type="button" class="btn btn-outline-primary" @click="registerUser">Register</button>
    </div>
  </div>
</template>
  
<script lang="ts">
import { defineComponent, reactive } from "vue";
import AuthServiceApi from "@/api/AuthServiceApi";
import router from "@/router";

export default defineComponent({
  setup() {
    const userInfo = reactive({
      fullname: "",
      email: "",
      phoneNumber: "",
      password: "",
      errorMessage: "",
    });

    async function registerUser() {
      try {
        const data = {
          fullName: userInfo.fullname,
          email: userInfo.email,
          phoneNumber: userInfo.phoneNumber,
          password: userInfo.password,
        };
        await AuthServiceApi.register(data);
        router.push({ path: "/auth/authenticate" });
      } catch (error) {
        userInfo.errorMessage = error.response.data;
      }
    }

    return {
      userInfo,
      registerUser,
    };
  },
});
</script>

<style scoped>
body {
  max-width: 2000px;
  padding-left: 0;
}

#registration {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: white;
}

.image {
  flex-basis: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  min-width: 50%;
  min-height: 50%;
}

.form {
  flex-basis: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 100px;
}

img {
  max-width: 100%;
  min-width: none;
  min-height: 50%;
  max-height: 100%;
  width: 100%;
}

input {
  border: 1px solid #39a0ca;
  border-radius: 5px 5px 5px 5px;
  width: 30%;
  height: 30px;
}

button {
  width: 100px;
}
</style>
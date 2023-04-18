<template>
<MainNavbar />

  <div id="window">
    <h2 id="selectRoom">Selected Room</h2>
    <div v-if="room" class="room-bubble">
      <div class="room">
        <div id="room-image">
          <img :src="require('../assets/images/RoomImages/' + room.image)" alt="room" :class="{ 'room-image-disabled': !room.status }"/>
          <div id="book" v-if="!room.status">Already booked</div>
        </div>
        <div class="roomInfo">
          <div id="room-title">{{ room.title }}</div>
          <div id="room-type">Type: {{ room.type }}</div>
          <div id="room-discription">Description: {{ room.description }}</div>
          <div id="room-price">Price: {{ room.price }} $ per night</div>
          <div id="bookingButton" v-if="hasTokenOrRefresh()">
            <a class="btn btn-primary" @click="showModal" role="button">Book this room</a>
            <ModalBook v-if="showModalBook" :price="room.price" />
          </div>
        </div>
      </div>
    </div>
  </div>

<MainFooter />
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import RoomService from "@/api/RoomServiceApi";
import MainNavbar from "@/components/MainNavbar.vue";
import MainFooter from "@/components/MainFooter.vue";
import { RouteLocationNormalized, useRoute } from "vue-router";
import ModalBook from "@/components/ModalBook.vue";
import hasTokenOrRefresh from "@/utils";

interface Room {
  id: number;
  type: string;
  number: number;
  title: string;
  description: string;
  image: string;
  price: number;
  status: boolean;
}

export default defineComponent({
  components: {
    MainNavbar,
    MainFooter,
    ModalBook,
  },
  setup() {
    const room = ref<Room | null>(null);
    const route: RouteLocationNormalized = useRoute();
    let typetitle = route.params.typetitle.toString();
    let title = route.params.title.toString();
    const showModalBook = ref(false);

    const getOneRoom = async (typetitle: string, title: string) => {
      try {
        const response = await RoomService.getOneByTitle(typetitle, title);
        room.value = response.data;
      } catch (error) {
        console.log(error);
      }
    };

    const showModal = () => {
      showModalBook.value = true;
    };

    getOneRoom(typetitle, title);
    return {
      room,
      typetitle,
      showModalBook,
      hasTokenOrRefresh,
      showModal,
    };
  },
});
</script>

<style scoped>
#window {
  margin-top: 20px;
  margin-bottom: 20px;
}

#selectRoom {
  margin: auto 0;
  text-align: center;
  padding-bottom: 20px;
}

.room-bubble {
  border: 1px solid wheat;
  background-color: #87ceeb;
  border-radius: 20px;
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  max-width: 1350px;
  margin: 0 auto;
}

.room {
  display: flex;
  align-items: center;
  width: 1000px;
}

#room-image {
  position: relative;
  text-align: center;
  color: white;
}

.room-image-disabled {
  content: "Cant book";
  opacity: 0.5;
}

#book {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 20px;
  color: black;
  font-weight: bold;
}

.roomInfo {
  margin-left: 16px;
}

#room-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 8px;
}

#room-discription {
  font-size: 20px;
  margin-bottom: 10px;
  width: 650px;
}

#room-type {
  font-size: 20px;
  margin-bottom: 10px;
}

#room-price {
  font-size: 18px;
  margin-bottom: 15px;
}

#room-image img {
  width: 100%;
  min-width: 600px;
  border-radius: 20px;
}

a {
  padding: 8px 16px;
}
</style>
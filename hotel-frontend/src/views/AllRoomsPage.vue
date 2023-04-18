<template>
<MainNavbar />

  <div id="allRooms">
    <h1 id="h1Rooms">All Rooms</h1>

    <div id="selectedFields">
      <div id="sort1">
        <label for="sortOption">Show:</label>
        <select id="sortOption2" v-model="params.direction" @change="setStatus">
          <option value="">Show only available rooms</option>
          <option value="true">Show all rooms</option>
        </select>
      </div>

      <div id="sort2">
        <label for="sortOption">Sort by:</label>
        <select id="sortOption" v-model="params.direction" @change="sortRooms">
          <option value="" selected></option>
          <option value="ASC">Price up</option>
          <option value="DESC">Price down</option>
        </select>
      </div>
    </div>

    <label for="arrival">Arrival Date</label>
    <input type="date" id="arrival" v-model="params.arrivalDate" min="2023-01-01" required @change="sortByDate"/>
    <label id="arrivalLabel" for="departure">Departure Date</label>
    <input type="date" id="departure" v-model="params.departureDate" min="2023-01-01" required @change="sortByDate"/>

    <div id="rooms">
      <div v-for="room in visibleRooms" :key="room.id" class="room-bubble">
        <div class="room">
          <div id="room-image">
            <img :src="require('../assets/images/RoomImages/' + room.image)" alt="room" :class="{ 'room-image-disabled': !room.status }"/>
            <div id="book" v-if="!room.status">Already book</div>
          </div>
          <div class="roomInfo">
            <div id="room-title">{{ room.title }}</div>
            <div id="room-type">Type: {{ room.type }}</div>
            <div id="room-price">Price: {{ room.price }} $ per night</div>
            <div id="showMore">
              <a class="btn btn-primary" :href="`#/myhotel/${room.type}/rooms/${room.title}`" role="button">Show room</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="shwMoreBtn" v-if="visibleRooms.length < rooms.length">
      <button class="btn btn-light" @click="showMoreRooms">Show more rooms</button>
    </div>
  </div>

<MainFooter />
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MainNavbar from "@/components/MainNavbar.vue";
import MainFooter from "@/components/MainFooter.vue";
import RoomService from "@/api/RoomServiceApi";

interface Room {
  id: number;
  type: string;
  title: string;
  image: string;
  price: number;
  status: boolean;
}

export default defineComponent({
  components: {
    MainNavbar,
    MainFooter,
  },
  setup() {
    const rooms = ref<Room[]>([]);
    const visibleRooms = ref<Room[]>([]);
    const roomsToShow = ref<number>(5);

    let params = {
      status: "",
      direction: "",
      arrivalDate: null,
      departureDate: null,
    };

    const getAllRooms = async (
      status: string,
      direction: string,
      arrivalDate: Date | null,
      departureDate: Date | null
    ) => {
      try {
        const response = await RoomService.getAll(
          status,
          direction,
          arrivalDate,
          departureDate
        );
        rooms.value = response.data;
        showMoreRooms();
      } catch (error) {
        console.log(error);
      }
    };

    getAllRooms("", "", null, null);

    const showMoreRooms = () => {
      visibleRooms.value = rooms.value.slice(0, roomsToShow.value);
      roomsToShow.value += 5;
    };

    const sortRooms = () => {
      getAllRooms(
        params.status,
        params.direction,
        params.arrivalDate,
        params.departureDate
      );
    };

    const setStatus = () => {
      if (params.status == "true") {
        params.status = "";
        getAllRooms(
          params.status,
          params.direction,
          params.arrivalDate,
          params.departureDate
        );
      } else {
        params.status = "true";
        getAllRooms(
          params.status,
          params.direction,
          params.arrivalDate,
          params.departureDate
        );
      }
    };

    const sortByDate = () => {
      if (
        (params.departureDate == null && params.arrivalDate != null) ||
        (params.arrivalDate != null &&
          params.departureDate != null &&
          params.departureDate < params.arrivalDate)
      ) {
        params.departureDate = params.arrivalDate;
      }

      if (params.departureDate != null && params.arrivalDate == null) {
        params.arrivalDate = params.departureDate;
      }

      getAllRooms(
        params.status,
        params.direction,
        params.arrivalDate,
        params.departureDate
      );
    };
    return {
      rooms,
      visibleRooms,
      params,
      showMoreRooms,
      sortRooms,
      setStatus,
      sortByDate,
    };
  },
});
</script>

<style scoped>
#allRooms {
  max-width: 1000px;
  margin: 0 auto;
  padding-bottom: 20px;
}

#selectedFields {
  display: flex;
  justify-content: space-between;
  gap: 100px;
}

#sort1,
#sort2 {
  display: flex;
  align-items: center;
}

label {
  margin-right: 10px;
  font-size: 20px;
}

select {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  background-color: #f5f5f5;
  color: #333;
  font-size: 15px;
  cursor: pointer;
}

select:focus {
  outline: none;
}

input {
  margin-top: 10px;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  background-color: #f5f5f5;
  color: #333;
  font-size: 15px;
  cursor: pointer;
  width: 30%;
}

input:focus {
  outline: none;
}

#arrivalLabel {
  margin-left: 140px;
}

img {
  max-width: 350px;
  border-radius: 20px;
  margin-right: 20px;
}

#h1Rooms {
  margin: auto 0;
  text-align: center;
  padding-top: 20px;
}

.room-bubble {
  margin-top: 20px;
  border: 1px solid wheat;
  background-color: #87ceeb;
  border-radius: 20px;
  display: flex;
  align-items: center;
  margin-bottom: 16px;
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

#room-type {
  font-size: 20px;
  margin-bottom: 10px;
}

#room-price {
  font-size: 18px;
  margin-bottom: 15px;
}

button {
  padding: 8px 16px;
}

a {
  padding: 8px 16px;
}

.shwMoreBtn {
  text-align: center;
  justify-content: center;
}
</style>

<template>
<MainNavbar />

  <div id="home">
    <div class="aboutUs">
      <div class="imageUs">
        <img id="mainPhoto" src="@/assets/images/HotelOutside.webp" alt="PhotoHotel"/>
      </div>
      <div class="textUs">
        <h1>ABOUT US</h1>
        <br/>
        <p>
          One of the main aspects of our hotel is its excellent location. We are
          located in a convenient location, in close proximity to major
          transport routes, as well as in close proximity to local attractions,
          beaches and business centers. This makes us the perfect destination
          for all types of travelers, whether families, business travelers or
          holidaymakers looking for relaxation and fun.
        </p>
        <br/>
        <a id="showRooms" href="#/myhotel/allrooms">Show all rooms</a>
      </div>
    </div>

    <hr id="blueLine" />

    <div id="Advantages">
      <h1>Advantages</h1>
      <div class="elAdvantages">
        <i class="bi bi-emoji-smile"></i>
        <span class="text">All people are happy :)</span>
      </div>
      <div class="elAdvantages">
        <i class="bi bi-cash"></i>
        <span class="text">We are cheap!</span>
      </div>
      <div class="elAdvantages">
        <i class="bi bi-brightness-alt-high"></i>
        <span class="text">Near the sea</span>
      </div>
      <div class="elAdvantages">
        <i class="bi bi-people"></i>
        <span class="text">Friendly staff</span>
      </div>
      <div class="elAdvantages">
        <i class="bi bi-binoculars"></i>
        <span class="text">Beautiful view</span>
      </div>
      <div class="elAdvantages">
        <i class="bi bi-broadcast-pin"></i>
        <span class="text">Free Wi-Fi</span>
      </div>
      <div class="elAdvantages">
        <i class="bi bi-trophy"></i>
        <span class="text">Many awards</span>
      </div>
      <div class="elAdvantages">
        <i class="bi bi-playstation"></i>
        <span class="text">Free play zone</span>
      </div>
    </div>

    <hr id="blueLine" />

    <div class="type">
      <h1>Rooms</h1>
      <div v-for="oneType in roomTypes" :key="oneType.id" class="onetype">
        <a id="aWithPic" :href="'#/myhotel/' + oneType.title + '/rooms'">
          <div :class="'pic' + oneType.id">
            <div class="overlay"></div>
            <h3>{{ oneType.title }} rooms</h3>
          </div>
        </a>
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

interface RoomType {
  id: number;
  title: string;
}

export default defineComponent({
  components: {
    MainNavbar,
    MainFooter,
  },
  setup() {
    const roomTypes = ref<RoomType[]>([]);
    const getRoomTypes = async () => {
      try {
        const response = await RoomService.getTypes();
        roomTypes.value = response.data;
      } catch (error) {
        console.error(error);
      }
    };

    getRoomTypes();

    return {
      roomTypes,
    };
  },
});
</script>

<style>
#home {
  background-color: #fffafa;
  max-width: 2000px;
  margin: 0 auto;
  padding-top: 20px;
  padding-bottom: 40px;
}

.aboutUs {
  display: flex;
  align-items: center;
  margin-bottom: 40px;
}

.imageUs {
  flex: 0 0 auto;
  margin-left: 30px;
}

#showRooms {
  color: #00bfff;
}

.textUs {
  flex: 1;
  text-align: center;
  font-size: 25px;
  margin-right: 50px;
}

.imageUs img {
  width: 90%;
  border-radius: 30px 30px 30px 30px;
}

#blueLine {
  display: block;
  width: 80%;
  border-width: 2px;
  border-color: #00bfff;
}

#Advantages {
  margin: 0 auto;
  max-width: 80%;
  text-align: center;
  margin-bottom: 40px;
}

.elAdvantages {
  text-align: center;
  display: inline-block;
  margin-right: 50px;
}

.elAdvantages i {
  display: block;
  font-size: 50px;
}

.elAdvantages span {
  font-size: 20px;
}

h1 {
  text-align: center;
}

#aWithPic {
  display: block;
}


.onetype {
  width: 50%;
  margin: 0 auto;
}

.pic1 {
  position: relative;
  background: url("@/assets/images/Single.webp") no-repeat center;
  width: 100%;
  margin: 0 auto;
  height: 300px;
  margin-top: 20px;
  margin-bottom: 50px;
  background-size: 100%;
}

.pic2 {
  position: relative;
  background: url("@/assets/images/Double.webp") no-repeat center;
  width: 100%;
  margin: 0 auto;
  height: 300px;
  margin-top: 20px;
  margin-bottom: 50px;
  background-size: 100%;
}

.pic3 {
  position: relative;
  background: url("@/assets/images/Triple.webp") no-repeat center;
  width: 100%;
  margin: 0 auto;
  height: 300px;
  background-size: 100%;
}

.overlay {
  position: absolute;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  z-index: 2;
}

h3 {
  z-index: 3;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
  font-size: 50px;
}
</style>
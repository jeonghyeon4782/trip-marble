<script setup>
import { ref, defineEmits, defineProps } from "vue";
import axios from "axios";

const diceVal1 = ref(null);
const diceVal2 = ref(null);
const nowLocation = ref(null);
const islandCnt = ref(null);

var rollTimeout;
const emit = defineEmits(['moveHorse'])
const props = defineProps({
  mapId: Number,
  isRolling: Boolean
});

const setVal = (num, val) => {
  var dices = document.querySelectorAll(".dice");
  var dice = dices.item(num - 1);
  if (!dice) return;
  dice.setAttribute("data-val", val);
}

const toggleRoll = () => {
  setVal(1, 0);
  setVal(2, 0);
}

const getRand = () => {
  return Math.round(Math.random() * 5 + 1);
}

const setVals = () => {
  diceVal1.value = getRand();
  diceVal2.value = getRand();
  setVal(1, diceVal1.value);
  setVal(2, diceVal2.value);
}

const rollDice = () => {
  if (rollTimeout) clearTimeout(rollTimeout);
  toggleRoll();
  rollTimeout = setTimeout(async function () {
    setVals();
    await updateNow(diceVal1.value, diceVal2.value); 
    emit('moveHorse', [diceVal1.value, diceVal2.value, nowLocation.value, islandCnt.value]);
  }, 1000);
}

const updateNow = async (diceVal1, diceVal2) => {
  try {
    const data = {
      memberBoardMapId: props.mapId,
      diceVal1: diceVal1,
      diceVal2: diceVal2
    };
    const response = await axios.post(`http://localhost:8080/api/board/dice`, data);
    nowLocation.value = response.data.data.nowLocation;
    islandCnt.value = response.data.data.islandCnt;
  } catch (error) {
    console.error('Error fetching attraction info:', error);
  }
}
</script>

<template>
  <div class="dice-container">
    <div class="dice dice_1">
      <div class="cube">
        <div class="side side_1">
          <span class="dot dot_5"></span>
        </div>
        <div class="side side_2">
          <span class="dot dot_3"></span><span class="dot dot_7"></span>
        </div>
        <div class="side side_3">
          <span class="dot dot_3"></span><span class="dot dot_5"></span
          ><span class="dot dot_7"></span>
        </div>
        <div class="side side_4">
          <span class="dot dot_1"></span><span class="dot dot_3"></span
          ><span class="dot dot_7"></span><span class="dot dot_9"></span>
        </div>
        <div class="side side_5">
          <span class="dot dot_1"></span><span class="dot dot_3"></span
          ><span class="dot dot_5"></span><span class="dot dot_7"></span
          ><span class="dot dot_9"></span>
        </div>
        <div class="side side_6">
          <span class="dot dot_1"></span><span class="dot dot_4"></span
          ><span class="dot dot_7"></span><span class="dot dot_3"></span
          ><span class="dot dot_6"></span><span class="dot dot_9"></span>
        </div>
      </div>
    </div>

    <div class="dice dice_2">
      <div class="cube">
        <div class="side side_1">
          <span class="dot dot_5"></span>
        </div>
        <div class="side side_2">
          <span class="dot dot_3"></span><span class="dot dot_7"></span>
        </div>
        <div class="side side_3">
          <span class="dot dot_3"></span><span class="dot dot_5"></span
          ><span class="dot dot_7"></span>
        </div>
        <div class="side side_4">
          <span class="dot dot_1"></span><span class="dot dot_3"></span
          ><span class="dot dot_7"></span><span class="dot dot_9"></span>
        </div>
        <div class="side side_5">
          <span class="dot dot_1"></span><span class="dot dot_3"></span
          ><span class="dot dot_5"></span><span class="dot dot_7"></span
          ><span class="dot dot_9"></span>
        </div>
        <div class="side side_6">
          <span class="dot dot_1"></span><span class="dot dot_4"></span
          ><span class="dot dot_7"></span><span class="dot dot_3"></span
          ><span class="dot dot_6"></span><span class="dot dot_9"></span>
        </div>
      </div>
    </div>
  </div>
  <button class="button" v-if="!isRolling" @click="rollDice">굴리기</button>
</template>

<style scoped>
.dice-container {
  display: flex;
  justify-content: center;
}

.dice {
  width: 100px;
  height: 100px;
  margin: 50px;
  perspective: 250px;
}

.cube {
  position: relative;
  width: 100px;
  height: 100px;
  transform-style: preserve-3d;
  transform: translateZ(-50px) rotateX(0) rotateY(0);
  transition: transform 1s cubic-bezier(0.215, 0.61, 0.355, 1);
}

.side {
  position: absolute;
  width: 100px;
  height: 100px;
  box-sizing: border-box;
  border: 1px solid rgba(255, 255, 255, 0.7);
  border-radius: 2px;
  overflow: hidden;
  background: white;
  backface-visibility: hidden;
}
.side_1 {
  transform: translateZ(-50px) rotateX(180deg);
}
.side_2 {
  transform: translateY(-50px) rotateX(90deg);
}
.side_3 {
  transform: translateX(50px) rotateY(90deg);
}
.side_4 {
  transform: translateX(-50px) rotateY(270deg);
}
.side_5 {
  transform: translateY(50px) rotateX(270deg);
}
.side_6 {
  transform: translateZ(50px);
}

.dice[data-val="1"] .cube {
  transform: translateZ(-50px) rotateX(160deg) rotateY(20deg); 
}

.dice[data-val="2"] .cube {
  transform: translateZ(-50px) rotateX(-90deg) rotateY(0deg) rotateZ(-20deg); 
}

.dice[data-val="3"] .cube {
  transform: translateZ(-50px) rotateX(-20deg) rotateY(-70deg);
}

.dice[data-val="4"] .cube {
  transform: translateZ(-50px) rotateX(-20deg) rotateY(70deg);
}

.dice[data-val="5"] .cube {
  transform: translateZ(-50px) rotateX(70deg) rotateY(0deg) rotateZ(-20deg);
}

.dice[data-val="0"] .cube {
  transition: transform 1s cubic-bezier(0.55, 0.055, 0.675, 0.20);
}

.dice_1[data-val="0"] .cube {
  transform: translateZ(-50px) rotateX(359deg) rotateY(359deg);
}

.dice_2[data-val="0"] .cube {
  transform: translateZ(-50px) rotateX(-359deg) rotateY(-359deg);
}

.dot {
  position: absolute;
  width: 20px;
  height: 20px;
  margin: -10px 0 0 -10px;
  border-radius: 10px;
  background: black;
  box-shadow: inset 0 0 10px rgba(204, 0, 0, 0.5);
}
.dot_1,
.dot_2,
.dot_3 {
  top: 20px;
}
.dot_4,
.dot_5,
.dot_6 {
  top: 50px;
}
.dot_7,
.dot_8,
.dot_9 {
  top: 80px;
}
.dot_1,
.dot_4,
.dot_7 {
  left: 20px;
}
.dot_2,
.dot_5,
.dot_8 {
  left: 50px;
}
.dot_3,
.dot_6,
.dot_9 {
  left: 80px;
}

.button {
  width: 140px;
  height: 45px;
  font-family: 'Roboto', sans-serif;
  font-size: 15px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 500;
  color: #000;
  background-color: aliceblue;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  }

.button:hover {
  background-color: #2EE59D;
  box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
  color: #fff;
  transform: translateY(-7px);
}
</style>
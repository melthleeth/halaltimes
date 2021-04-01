<template>
  <div v-if="open" class="backdrop" @click="$emit('close')">
  </div>
  <transition name="modal">
    <dialog open v-if="open" :class="custom">
      <slot></slot>
    </dialog>
  </transition>
</template>

<script>
export default {
  props: {
    open: {
      type: Boolean,
      required: false,
      default: false,
    },
    custom: {
      type: String,
      required: false,
      default: null,
    },
  },
  emits: ["close"],
};
</script>

<style scoped>
.backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  z-index: 10;
  background-color: rgba(0, 0, 0, 0.6);
}

dialog {
  background-color: white;
  position: fixed;
  top: 20vh;
  width: 20rem;
  left: calc(50% - 10rem);
  margin: 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
  border: 4px solid #2B2A29;
  border-radius: 0 0 30px 0;
  padding: 1rem;
  z-index: 100;
  /* animation: modal 0.3s ease-out forwards; */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.width-wide {
  width: 40rem;
  left: calc(50% - 20rem);
}

.modal-enter-active {
  animation: modal 0.3s ease-out;
}

.modal-leave-active {
  animation: modal 0.3s ease-in reverse;
}

@keyframes modal {
  from {
    opacity: 0;
    transform: translateY(-50px) scale(0.9);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.width-wide {
  width: 26rem;
  top: 20vh;
  left: calc(50% - 13rem);
}
</style>

<template>
  <button v-if="!link" :class="mode">
    <slot></slot>
  </button>
  <router-link v-else :to="to" :class="mode">
    <slot></slot>
  </router-link>
</template>

<script>
export default {
  props: {
    mode: {
      type: String,
      required: false,
      default: null,
    },
    link: {
      type: Boolean,
      required: false,
      default: false,
    },
    to: {
      type: String,
      required: false,
      default: "/",
    },
  },
};
</script>

<style scoped>
:root {
  --primary-color: #CF4F2E;
  --primary-color-hover: #903720;
  
  --secondary-color: #4F8163;
  --secondary-color-hover: #2F4D3B;

  --base-color: #2B2A29;
  --base-color-hover: #6A6969;

  --important-color: #FFF58C;
}

button,
a {
  text-decoration: none;
  padding: 0.25rem 1rem;
  /* font: inherit; */
  color: white;
  background-color: #2B2A29;
  border: 3px solid #2B2A29;
  cursor: pointer;
  /* border-radius: 30px; */
  /* margin-right: 0.5rem; */
  display: inline-block;
  transition: 0.3s ease-out;
}

a:hover,
a:active,
button:hover,
button:active {
  background-color: #6A6969;
  border: 3px solid #6A6969;
}

a:focus,
button:focus {
  outline: none;
}

.important {
  background-color: transparent;
  color: #2B2A29;
  border: 3px solid #2B2A29;
  padding: 0.25rem 0.75rem;
}

.important:hover,
.important:active {
  background-color: #FFF58C;
}

.primary {
  color: white;
  background-color: #CF4F2E;
  border: 3px solid #CF4F2E;
}

.primary:hover {
  background-color: #903720;
  border: 3px solid #903720;
}

.secondary {
  color: white;
  background-color: #4F8163;
  border: 3px solid #4F8163;
}

.secondary:hover {
  background-color: #2F4D3B;
  border: 3px solid #2F4D3B;
}

.outline_primary {
  color: #CF4F2E;
  background-color: transparent;
  border: 3px solid #CF4F2E;
}

.outline_primary:hover {
  color: white;
  background-color: #CF4F2E;
}

.outline_secondary {
  color: #4F8163;
  background-color: transparent;
  border: 3px solid #4F8163;
}

.outline_secondary:hover {
  color: white;
  background-color: #2F4D3B;
}

.outline {
  background-color: transparent;
  border: 3px solid #2B2A29;
  color: #2B2A29;
}

.outline:hover,
.outline:active {
  color: white;
  background-color: #00002f;
}

</style>

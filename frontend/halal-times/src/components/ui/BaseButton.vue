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
      default: null
    },
    link: {
      type: Boolean,
      required: false,
      default: false
    },
    to: {
      type: String,
      required: false,
      default: '/'
    }
  }
};
</script>

<style scoped>
:root {
  --primary-color: #cf4f2e;
  --primary-color-hover: #903720;

  --secondary-color: #4f8163;
  --secondary-color-hover: #2f4d3b;

  --base-color: #2b2a29;
  --base-color-hover: #6a6969;

  --important-color: #fff58c;
}

button,
a {
  text-decoration: none;
  padding: 0.25rem 1rem;
  /* font: inherit; */
  color: white;
  background-color: #2b2a29;
  border: 3px solid #2b2a29;
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
  background-color: #6a6969;
  border: 3px solid #6a6969;
}

a:focus,
button:focus {
  outline: none;
}

.important {
  background-color: transparent;
  color: #2b2a29;
  border: 3px solid #2b2a29;
  padding: 0.25rem 0.75rem;
}

.important:hover,
.important:active {
  background-color: #fff58c;
}

.primary {
  color: white;
  background-color: #cf4f2e;
  border: 3px solid #cf4f2e;
}

.primary:hover {
  background-color: #903720;
  border: 3px solid #903720;
}

.secondary {
  color: white;
  background-color: #4f8163;
  border: 3px solid #4f8163;
}

.secondary:hover {
  background-color: #2f4d3b;
  border: 3px solid #2f4d3b;
}

.brown {
  color: white;
  background-color: #D0AC73;
  border: 3px solid #D0AC73;
}

.brown:hover {
  background-color: #6C532C;
  border: 3px solid #6C532C;
}

.outline_primary {
  color: #cf4f2e;
  background-color: transparent;
  border: 3px solid #cf4f2e;
}

.outline_primary:hover {
  color: white;
  background-color: #cf4f2e;
}

.outline_secondary {
  color: #4f8163;
  background-color: transparent;
  border: 3px solid #4f8163;
}

.outline_secondary:hover {
  color: white;
  background-color: #2f4d3b;
}

.outline {
  background-color: transparent;
  border: 3px solid #2b2a29;
  color: #2b2a29;
}

.outline:hover,
.outline:active {
  color: white;
  background-color: #00002f;
}

.small-round-primary {
  padding: 0.2rem 0.65rem;
  font-size: 0.6rem;
  line-height: 0.85rem;
  color: #CF4F2E;
  background-color: transparent;
  border: 2.5px solid #CF4F2E;
  border-radius: 9999px;
  transition: .3s ease-out;
}

.small-round-primary:hover {
  color: white;
  background-color: #CF4F2E;
  border: 2.5px solid #CF4F2E;
}

.small-round-brown {
  padding: 0.2rem 0.65rem;
  font-size: 0.6rem;
  line-height: 0.85rem;
  color: #D0AC73;
  background-color: transparent;
  border: 2.5px solid #D0AC73;
  border-radius: 9999px;
  transition: .3s ease-out;
}

.small-round-brown:hover {
  color: white;
  background-color: #D0AC73;
  border: 2.5px solid #D0AC73;
}

</style>

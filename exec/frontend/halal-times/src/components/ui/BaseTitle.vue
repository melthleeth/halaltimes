<template>
    <section class="flex flex-col justify-center mt-4 mb-6">
      <span
        class="text-5xl w-full text-center py-1 mt-4 mb-1 font-outline work-sans border-line"
        >
        <slot></slot>
        </span
      >
      <article class="flex justify-between text-xs border-line font-bold">
        <span>VOL 1. No.1</span>
        <span>EVERYTHING BUT HALAL</span>
        <span>{{ currDate }}</span>
      </article>
    </section>
</template>
<script>
export default {
    computed: {
    currDate() {
      const event = new Date();
      const options = {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return event.toLocaleDateString(undefined, options);
    },
  },
}
</script>
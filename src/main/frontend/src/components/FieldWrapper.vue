<template>
  <div class="field-wrapper flex flex-col">
    <span class=""> {{ fieldTitle }} </span>
    <slot></slot>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import { Prop } from 'vue-property-decorator';

@Component
export default class FieldWrapper extends Vue {
  @Prop({ type: String, required: true }) fieldTitle!: string;
}
</script>

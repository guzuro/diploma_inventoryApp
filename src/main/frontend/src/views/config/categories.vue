<template>
  <a-spin :spinning="spinning">
    <div class="categories">
      <a-button
        @click="
          isCategoryModalOpen = true;
          modalMode = 'new';
        "
        class="mb-5"
        >Добавить категорию</a-button
      >
      <div v-if="categories && categories.length" class="flex flex-wrap">
        <div v-for="(category, index) in categories" :key="index" class="mb-2 md:mb-0 md:mr-2 w-full md:w-1/3">
          <a-card hoverable>
            <template slot="actions" class="ant-card-actions">
              <a-icon key="edit" type="edit" @click="doEdit(category)" />
              <a-popconfirm title="Действительно удалить категорию?" ok-text="Удалить" cancel-text="Отмена" @confirm="removeRole(category.id)">
                <a-icon key="remove" type="delete" />
              </a-popconfirm>
            </template>
            <a-card-meta :title="category.name" />
          </a-card>
        </div>
      </div>
      <div v-else>empty</div>
      <a-modal v-model="isCategoryModalOpen" :title="modalMode === 'new' ? 'Добавить' : 'Редактировать'">
        <field-wrapper fieldTitle="Название">
          <a-input v-model="category.name" />
        </field-wrapper>
        <template slot="footer">
          <a-button key="submit" @click="modalMode === 'new' ? addCategory() : updateCategory()">Сохранить</a-button>
        </template>
      </a-modal>
    </div>
  </a-spin>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { Watch } from 'vue-property-decorator';
import categoryService from '@/services/Config/CategoryService';
import FieldWrapper from '@/components/FieldWrapper.vue';
import { Category } from '@/types/Category';

@Component({
  components: {
    FieldWrapper,
  },
})
export default class Categories extends Vue {
  isCategoryModalOpen = false;

  @Watch('isCategoryModalOpen')
  onModalChange(state: boolean): void {
    if (!state) {
      this.category = {
        name: '',
      };
    }
  }

  spinning = false;

  modalMode = 'new';

  categories: Array<Category> | null = null;

  category: Category = {
    name: '',
  };

  get companyId(): number {
    return this.$store.getters['companyModule/getCompany'].id;
  }

  async removeRole(id: number): Promise<void> {
    await categoryService.deleteCategory({ category_id: id });
    this.getCategories();
  }

  doEdit(item: Category): void {
    this.category = { ...item };
    this.modalMode = 'edit';
    this.isCategoryModalOpen = true;
  }

  async updateCategory(): Promise<void> {
    await categoryService.updateCategory({ category: this.category });
    await this.getCategories();
  }

  async addCategory(): Promise<void> {
    await categoryService.addCategory({
      category: this.category,
      company_id: this.companyId,
    });
    await this.getCategories();
  }

  getCategories(): void {
    this.isCategoryModalOpen = false;
    this.spinning = true;
    this.$store
      .dispatch('configModule/REQUEST_SALES')
      .then(() => {
        this.categories = this.$store.state.configModule.category.slice();
      })
      .finally(() => {
        this.spinning = false;
      });
  }

  created(): void {
    this.getCategories();
  }
}
</script>

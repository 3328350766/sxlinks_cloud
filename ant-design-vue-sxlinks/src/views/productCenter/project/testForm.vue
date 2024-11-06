<template>
  <div>
    <a-form ref="serverForm" :model="serverForm" label-width="80px">
      <a-form-item label="项目名称" prop="projectName">
        <a-input v-model="serverForm.projectName" placeholder="请输入项目名称" />
      </a-form-item>

      <a-form-item label="音柱服务器" prop="phonogramType">
        <a-select
          v-model="serverForm.phonogramType"
          placeholder="请选择音柱服务器"
          clearable
        >
          <a-option
            v-for="item in phonogramTypeList"
            :key="item.id"
            :label="item.sdkName"
            :value="item.id"
          />
        </a-select>
      </a-form-item>
      <a-form-item label="监控服务器" prop="monitorType">
        <a-select
          v-model="serverForm.monitorType"
          placeholder="请选择监控服务器"
          clearable
        >
          <a-option
            v-for="item in monitorTypeList"
            :key="item.id"
            :label="item.sdkName"
            :value="item.id"
          />
        </a-select>
      </a-form-item>
    </a-form>
    <div slot="footer" class="dialog-footer">
      <a-button type="primary" @click="submitServerForm">确 定</a-button>
      <a-button @click="closeYm">取 消</a-button>
    </div>
  </div>
</template>

<script>
// import {
//   listProjectManagement,
//   queryProjectSdk,
//   getProjectManagement,
//   delProjectManagement,
//   addProjectManagement,
//   addConfigureServer,
//   updateProjectManagement,
// } from "@/api/management/projectManagement";
export default {
  props: {
    rowForm: Object,
    phonogramTypeList: Array,
    monitorTypeList: Array,
  },
  data() {
    return {
      //服务器表单
      serverForm: {
        projectId: null,
        projectName: null,
        phonogramType: null,
        monitorType: null,
      },
      subForm: {},
    };
  },
  mounted() {
    // this.getServerFormData();
  },
  methods: {
    // 查询回显数据
    getServerFormData() {
      queryProjectSdk(this.rowForm).then((response) => {
        console.log(response);
        this.params = response.data;
        this.serverForm.projectName = this.params.projectName;
        this.serverForm.phonogramType = this.params.phonogramType.toString();
        this.serverForm.monitorType = this.params.monitorType.toString();
        this.serverForm.projectId = this.params.projectId;
      });
    },
    getParams() {},
    closeYm() {
      this.$emit("close");
    },
    submitServerForm() {
      this.subForm.phonogramType = this.serverForm.phonogramType;
      this.subForm.monitorType = this.serverForm.monitorType;
      this.subForm.projectId = this.serverForm.projectId;
      addConfigureServer(this.subForm).then((response) => {
        this.$modal.msgSuccess("配置服务器成功");
        this.closeYm();
      });
    },
  },
};
</script>

<style scoped></style>

<template>
  <div>
    <el-dialog :visible="configPlugDialogVisible" title="配置插件" append-to-body @close="close">
      <el-form ref="configPlug">
        <el-form-item>
          用户参数配置:
          <el-table
            :data="userParamTable"
            style="width: 100%;
            background-color:white"
            empty-text="暂无数据"
          >
            <el-table-column
              prop="name"
              label="参数名"
            />
            <el-table-column
              prop="value"
              label="参数值"
            />
            <el-table-column
              label="操作"
            >
              <template v-slot="data">
                <el-button type="text" size="small" @click="openUserParamDialog('编辑用户参数',data.row.id)">编辑</el-button>
                <el-button type="text" size="small" style="color: red" @click="delUserPara(data.row.id)">删除</el-button>
              </template>
            </el-table-column></el-table>
          <el-form-item />
          <el-form-item>
            <el-row type="flex" align="middle" justify="center">
              <el-button type="text" @click="openUserParamDialog('添加用户参数','0')">添加参数</el-button>
            </el-row>
          </el-form-item>
          插件参数定义：
          <el-form-item>
            <el-table
              :data="plugParamTable"
              style="width: 100%;
              background-color:white"
              empty-text="暂无数据"
            >
              <el-table-column
                prop="name"
                label="字段名称"
              />
              <el-table-column
                prop="field"
                label="字段"
              />
              <el-table-column
                prop="type"
                label="类型"
                :formatter="formatType"
              />
              <el-table-column
                prop="necessary"
                label="是否必填"
                :formatter="formatNecessary"
              />
              <el-table-column
                prop="description"
                label="描述"
              /><el-table-column
                label="操作"
              >
                <template v-slot="data">
                  <el-button type="text" size="small" @click="openPlugParamDialog('编辑插件参数',data.row.id)">编辑</el-button>
                  <el-button type="text" size="small" style="color: red" @click="delPlugPara(data.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item>
            <el-row type="flex" align="middle" justify="center">
              <el-button type="text" @click="openPlugParamDialog('添加插件参数','0')">添加参数</el-button>
            </el-row>
          </el-form-item>
          <el-form-item>
            <el-row>
              <el-col span="10">
                <el-button type="primary" @click="close">关闭</el-button>
                <el-button type="primary" @click="confirm">确认</el-button>
              </el-col>
            </el-row>
          </el-form-item></el-form-item></el-form>

      <!--    添加插件参数配置-->
      <plug-param :plug-id.sync="plugId" :plug-para-id.sync="plugParaId" :dialog-title="plugParamDialogTitle" :plug-param-dialog-visible.sync="plugParamDialogVisible" @add-item="addItemToList" />
      <!--    添加插件参数配置-->
      <!--    添加用户参数配置-->
      <user-param :plug-id.sync="plugId" :user-para-id.sync="userParaId" :dialog-title="userParamDialogTitle" :user-param-dialog-visible.sync="userParamDialogVisible" @add-item="addItemToList" />
      <!--    添加用户参数配置-->
    </el-dialog>
  </div>
</template>
<script>

import UserParam from '@/views/appInfoManagement/plugManagement/components/UserParam.vue'
import PlugParam from '@/views/appInfoManagement/plugManagement/components/PlugParam.vue'
import {
  delPlugParaById,
  delUserParaById,
  getPlugById
} from '@/api/plug'
import { getTypeUnions } from '@/api/common'
export default {
  components: { UserParam, PlugParam },
  props: {
    configPlugDialogVisible: {
      type: Boolean,
      default: false
    },
    plugId: {
      type: String,
      default: '0'
    }
  },
  data() {
    return {
      plugParaId: '0',
      userParaId: '0',
      plugParamDialogVisible: false,
      plugParamDialogTitle: '',
      userParamDialogVisible: false,
      userParamDialogTitle: '',
      typeList: [],
      plugParamTable: [
        {
          name: ''
        }],
      userParamTable: [{
        name: '',
        field: '',
        type: '',
        necessary: '',
        description: ''
      }]
    }
  },
  watch: {
    plugId(newValue) {
      if (newValue === '0') {
        this.plugParamTable = [
          { name: '' }
        ]
        this.userParamTable = [
          { name: '',
            field: '',
            type: '',
            necessary: '',
            description: '' }
        ]
      } else {
        getPlugById(newValue).then(response => {
          this.plugParamTable = response.data.plugParas
          this.userParamTable = response.data.userParas
        })
      }
    }
  },
  mounted() {
    getTypeUnions().then(response => {
      this.typeList = response.data
    })
  },
  methods: {
    formatNecessary(row) {
      return row.necessary === '1' ? '是' : '否'
    },
    formatType(row) {
      return this.typeList[row.type - 1].type
    },
    close() {
      this.$emit('update:configPlugDialogVisible', false)
    },
    openPlugParamDialog(title, id) {
      this.plugParamDialogVisible = true
      this.plugParamDialogTitle = title
      this.plugParaId = id
    },
    openUserParamDialog(title, id) {
      this.userParamDialogVisible = true
      this.userParamDialogTitle = title
      this.userParaId = id
    },
    delPlugPara(id) {
      this.$confirm('确定删除该插件参数？').then(() => {
        delPlugParaById(id).then(response => {
          if (response.errno === 0) {
            this.$message.success('删除插件参数成功！')
          } else {
            this.$message.error('删除插件参数失败')
          }
        })
      })
    },
    delUserPara(id) {
      this.$confirm('确定删除该用户参数？').then(() => {
        delUserParaById(id).then(response => {
          if (response.errno === 0) {
            this.$message.success('删除用户参数成功！')
          } else {
            this.$message.error('删除用户参数失败')
          }
        })
      })
    },
    addItemToList(type) {
      switch (type) {
        case 'addPlugPara':
          getPlugById(this.plugId).then(response => {
            this.plugParamTable = response.data.plugParas
          })
          break
        case 'addUserPara':
          getPlugById(this.plugId).then(response => {
            this.userParamTable = response.data.userParas
          })
          break
      }
    }
  }
}
</script>

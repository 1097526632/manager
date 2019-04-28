<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogVisible"
            width="70%"
            v-dialogDrag
            @closed="closeFormDialog"
            :close-on-click-modal="false"
    >
        <div class="form-wrapper">
            <el-form ref="form" :model="form" :class="showFooter === false ?'view':''" :rules="rules"  label-width="80px">
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="上级机构:" prop="parentName">
                      <el-input v-model="form.parentName" :readonly="true" @click.native="showParent()"></el-input>
                    </el-form-item>
                  </el-col>
                    <el-col :span="12">
                        <el-form-item label="名称:" prop="name">
                            <el-input v-model="form.name"></el-input>
                        </el-form-item>
                    </el-col>
                  <el-col :span="12">
                    <el-form-item label="机构类型:" prop="type">
                      <el-select  v-model="form.type" placeholder="请选择">
                        <el-option v-for="(item, index) in typeDict" :key="index"
                                :label="item.label"
                                :value="item.value"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                    <el-col :span="12">
                        <el-form-item label="排序:" prop="sort">
                            <el-input v-model="form.sort"></el-input>
                        </el-form-item>
                    </el-col>
                  <el-col :span="12">
                    <el-form-item label="邮政编码:" prop="zipCode">
                      <el-input v-model="form.zipCode"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="是否启用:" prop="useable">
                      <el-radio-group v-model="form.useable">
                        <el-radio label="1">启用</el-radio>
                        <el-radio label="0">禁用</el-radio>
                      </el-radio-group>
                    </el-form-item>
                  </el-col>

                    <el-col :span="24">
                        <el-form-item label="联系地址:" prop="address">
                            <el-input type="textarea" v-model="form.address"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>
            </el-form>
        </div>
        <span slot="footer" class="dialog-footer" v-if="showFooter">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmInfo()">确 定</el-button>
        </span>

      <tree-dialog ref="treeDialog" :load-url="'sys/sysOffice/loadList'" :rootParams="{parentId: 'o_0'}" @treeConfirm="confirmParent"></tree-dialog>
    </el-dialog>
</template>

<script>
import {saveInfo} from "@/assets/js/api/app_api";
import {hasBtnPermission} from "@/assets/js/business/app_business";
import {getDict} from "../../../../assets/js/dict";
import {post} from "../../../../assets/js/api/app_api";
import constants from "../../../../assets/js/constants";
import TreeDialog from "../../../common/plugin/treedialog/treeDialog";

export default {
    name: 'form',
  components: {TreeDialog},
  computed: {
    typeDict: function () {
      return getDict('sys_office_type')
    }
  },
  data () {
        return {
            title: '',
            dialogVisible: false,
            treeVisiable: false,
            treeOpen:false,
          treeDialogOpen:false,
          rootNode: {},
            showFooter: true,
            form: {},
            rules: {
                name: [
                    {required: true, message: '请输入名称', trigger: 'blur'}
                ],
                sort: [
                    {required: true, message: '请输入排序', trigger: 'blur'}
                ]
            },
          treeProps: {
            label: 'name',
            children: 'children',
            isLeaf: 'leaf'
          }
        }
    },
    methods: {
        confirmInfo () {
          this.form.__parent = null
          this.form.children = null
            saveInfo(this, 'form', 'sys/sysOffice/save', this.form)
        },
        hasBtnPermission (key) {
            return hasBtnPermission(key)
        },
        showDialog(title, visiable, data, footFlag) {
            this.title = title
            this.dialogVisible = visiable
            this.form = Object.assign({}, data)
            this.showFooter = footFlag? footFlag === 0 : true
        },
      showParent () {
        this.$refs['treeDialog'].showDialog('选择上级机构')
      },
      treeClosed(){
        this.treeDialogOpen = false
        this.rootNode.node.childNodes= []
      },
        closeFormDialog (){
            this.$refs['form'].resetFields()
        },
      confirmParent (node) {
        if (node) {
          if (node.data.id === this.form.id) {
            this.$message.warning('上级机构不能选择自身')
            return false
          } else {
            this.$set(this.form,'parentId',node.data.id)
            this.$set(this.form,'parentName',node.data.name)
          }
        }
      }
    }
}
</script>

<style scoped lang='scss'>
    .form-wrapper{
        min-height:300px;
        height:100%;
        padding:5px 10px;
    }
  .el-select{
    width:100%;
  }
    .tree-container{
      height:400px;
      overflow:auto;
    }
</style>

<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogVisible"
            width="70%"
            @closed="closeFormDialog"
            v-dialogDrag
            :close-on-click-modal="false"
    >
        <div class="form-wrapper">
            <el-form ref="form" :model="form" :class="showFooter === false ?'view':''"  :rules="rules"  label-width="80px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="编码:" prop="code">
                            <el-input v-model="form.code" :readonly="codeRead"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="值:" prop="value">
                            <el-input v-model="form.value"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="描述:" prop="description">
                            <el-input v-model="form.description"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <span slot="footer" class="dialog-footer" v-if="showFooter">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmInfo()">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
import {saveInfo} from "@/assets/js/api/app_api";
import {hasBtnPermission} from "@/assets/js/business/app_business";

export default {
    name: 'form',
    data () {
        return {
            title: '',
            dialogVisible: false,
            showFooter: true,
            form: {},
            rules: {
              code:[
                {required: true, message: '请输入编码', trigger: 'blur'}
              ],
              value:[
                {required: true, message: '请输入值', trigger: 'blur'}
              ],
              description:[
                {required: true, message: '请输入描述', trigger: 'blur'}
              ]
            },
        }
    },
    computed:{
      codeRead () {
        return (this.form.type == 0)
      }
    },
    methods: {
        confirmInfo () {
            saveInfo(this, 'form', 'sys/sysParam/save', this.form)
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
      closeFormDialog () {
        this.$refs['form'].resetFields()
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
</style>

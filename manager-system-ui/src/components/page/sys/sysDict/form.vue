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
            <el-form ref="form" :model="form" :class="showFooter === false ?'view':''"   :rules="rules"  label-width="80px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="数据值:" prop="value">
                            <el-input v-model="form.value"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="标签名:" prop="label">
                            <el-input v-model="form.label"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类型:" prop="type">
                            <el-input v-model="form.type"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="描述:" prop="description">
                            <el-input v-model="form.description"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="排序:" prop="sort">
                            <el-input v-model="form.sort"></el-input>
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
import {validateNum} from "../../../../assets/js/validate";

export default {
    name: 'form',
    data () {
        return {
            title: '',
            dialogVisible: false,
            showFooter: true,
            form: {},
            rules: {
              value:[
                {required: true, message: '请输入值', trigger: 'blur'}
                ],
              label: [
                {required: true, message: '请输入标签', trigger: 'blur'}
              ],
              type: [
                {required: true, message: '请输入类型', trigger: 'blur'}
              ],
              sort: [
                {required: true, message: '请输入排序', trigger: 'blur'},
                {validator: validateNum, trigger: 'blur'}
              ]
            }
        }
    },
    methods: {
        confirmInfo () {
            saveInfo(this, 'form', 'sys/sysDict/save', this.form)
        },
        hasBtnPermission (key) {
            return hasBtnPermission(key)
        },
        closeFormDialog (){
          this.$refs['form'].resetFields()
        },
        showDialog(title, visiable, data, footFlag) {
            this.title = title
            this.dialogVisible = visiable
            this.form = Object.assign({}, data)
            this.showFooter = footFlag? footFlag === 0 : true
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

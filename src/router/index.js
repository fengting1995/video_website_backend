import vue from "vue";
import VueRouter from "vue-router";

vue.use(VueRouter)

const routes =[
    {
        path:"/",
        name:"Home",
        component:()=>import("@/views/Home")
    },
    {
        path: "/play",
        name: "play",
        component:()=>import("@/views/Play")
    },
    {
        path: "/list",
        name: "list",
        component:()=>import("@/views/VideoList")
    },
    {
        path: "/collect",
        name: "collect",
        component:()=>import("@/views/Collect")
    }
]

const router = new VueRouter({
    // mode:history,
    routes
})

export default router
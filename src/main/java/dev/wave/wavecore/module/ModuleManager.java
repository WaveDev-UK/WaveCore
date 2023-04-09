package dev.wave.wavecore.module;

import java.util.*;

public class ModuleManager {

    private final Map<Class<? extends Module>, Module> moduleMap;

    public ModuleManager(){
        this.moduleMap = new HashMap<>();
    }

    public <T extends Module> T getModule(Class<T> module){
        return (T) moduleMap.get(module);
    }

    public void initialiseModules(Module... modules){
        for(Module module: modules){
            moduleMap.put(module.getClass(), module);
            System.out.println("Initialised Module " +  module.getClass().getName());
        }
    }

    private List<Module> sortModules(Collection<Module> modules) {
        final List<Module> sorted = new ArrayList<>();
        final Set<Class<? extends Module>> visited = new HashSet<>();

        for (Class<? extends Module> module : moduleMap.keySet()) {
            visit(module, visited, sorted);
        }

        return sorted;
    }

    private void visit(Class<? extends Module> module, Set<Class<? extends Module>> visited, List<Module> sorted) {

        final Module moduleClass = getModule(module);

        if (!visited.contains(module)) {
            visited.add(module);

            for (Class<? extends Module> moduleDependency : moduleClass.getDependencies()) {
                visit(moduleDependency, visited, sorted);
            }

            sorted.add(moduleClass);
        } else {
            if (!sorted.contains(moduleClass)) {
                throw new IllegalStateException(String.format("Cyclic dependency with %s", moduleClass.getName()));
            }
        }
    }

    public void loadModules() {
        for (Module module : sortModules(moduleMap.values())) {
            module.enable();
        }
    }

    public void disableModules(){
        List<Module> modules = sortModules(moduleMap.values());
        Collections.reverse(modules);
        for(Module module: modules){
            module.disable();
        }
    }

}

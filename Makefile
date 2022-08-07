TARGET_DIR=./bin
TARGET_ORIGIN_DIR=./bin/origin
BUILD_NUM=$$(cat deploy/build_num.txt)
BUILD_NUM_FILE=deploy/build_num.txt
MODULE_NAME=withpet-api
PID_NAME=withpet-api.jar
API_MODULE_NAME=module-api
API_DIR=./module-api/build/libs/
API_PID=withpet-api.jar

api: config build_num boot_jar move_jar

config:
	@if [ ! -d $(TARGET_DIR) ]; then mkdir $(TARGET_DIR); fi
	@if [ ! -d $(TARGET_ORIGIN_DIR) ]; then mkdir $(TARGET_ORIGIN_DIR); fi
	@if [ -e $(PID_NAME) ]; then mv $(PID_NAME) $(TARGET_ORIGIN_DIR); fi

build_num:
	@echo $$(($$(cat $(BUILD_NUM_FILE)) + 1 )) > $(BUILD_NUM_FILE)

boot_jar:
	./gradlew clean :$(API_MODULE_NAME):bootjar

move_jar:
	mv $(TARGET_DIR)/*.jar $(TARGET_ORIGIN_DIR)
	cp $(API_DIR)$(API_PID) $(TARGET_DIR)

docker_sh:
	bash -c ./docker_push.sh

clean:
	rm -rf $(TARGET_DIR)/$(MODULE_NAME)_$(VERSION)*

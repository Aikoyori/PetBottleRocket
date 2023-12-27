package xyz.aikoyori.petbottlerocket.client.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;

// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class WaterRocketModel extends EntityModel<WaterRocketEntity> {
	private final ModelPart cone;
	private final ModelPart base;
	private final ModelPart bottle;
	private final ModelPart fins;
	private final ModelPart fins_alternate;
	private final ModelPart water;
	private final ModelPart water_out;
	private final ModelPart lid;
	public WaterRocketModel(ModelPart root) {
		this.cone = root.getChild("cone");
		this.base = root.getChild("base");
		this.bottle = root.getChild("bottle");
		this.fins = root.getChild("fins");
		this.fins_alternate = root.getChild("fins_alternate");
		this.water = root.getChild("water");
		this.water_out = root.getChild("water_out");
		this.lid = root.getChild("lid");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData cone = modelPartData.addChild("cone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = cone.addChild("cube_r1", ModelPartBuilder.create().uv(0, 10).cuboid(-4.5F, -4.5F, 0.0F, 9.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -15.5F, 0.0F, -3.1416F, 0.7854F, 3.1416F));

		ModelPartData cube_r2 = cone.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -20.0F, 0.0F, 9.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(-12, 20).cuboid(-6.0F, -1.0F, -6.0F, 12.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData bottle = modelPartData.addChild("bottle", ModelPartBuilder.create().uv(0, 49).cuboid(-2.0F, -11.0F, -2.0F, 0.0F, 11.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 37).cuboid(2.0F, -11.0F, -2.0F, 0.0F, 11.0F, 4.0F, new Dilation(0.0F))
		.uv(17, 57).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(17, 58).cuboid(-2.0F, 0.0F, 1.0F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 60).cuboid(-2.0F, -11.0F, -2.0F, 4.0F, 0.0F, 4.0F, new Dilation(0.0F))
		.uv(22, 53).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(18, 53).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r3 = bottle.addChild("cube_r3", ModelPartBuilder.create().uv(22, 54).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(18, 54).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData cube_r4 = bottle.addChild("cube_r4", ModelPartBuilder.create().uv(21, 56).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(17, 56).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(9, 49).cuboid(-2.0F, -11.0F, -2.0F, 0.0F, 11.0F, 4.0F, new Dilation(0.0F))
		.uv(9, 37).cuboid(2.0F, -11.0F, -2.0F, 0.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData fins = modelPartData.addChild("fins", ModelPartBuilder.create().uv(52, 7).cuboid(0.0F, -12.0F, 2.0F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F))
		.uv(52, -6).cuboid(0.0F, -12.0F, -8.0F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 28.0F, 0.0F));

		ModelPartData cube_r5 = fins.addChild("cube_r5", ModelPartBuilder.create().uv(39, -6).cuboid(0.0F, -12.0F, -8.0F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F))
		.uv(39, 7).cuboid(0.0F, -12.0F, 2.0F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData fins_alternate = modelPartData.addChild("fins_alternate", ModelPartBuilder.create().uv(52, 7).cuboid(0.0F, -12.0F, 2.8F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F))
		.uv(52, -6).cuboid(0.0F, -12.0F, -8.8F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 28.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData cube_r6 = fins_alternate.addChild("cube_r6", ModelPartBuilder.create().uv(39, -6).cuboid(0.0F, -12.0F, -8.8F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F))
		.uv(39, 7).cuboid(0.0F, -12.0F, 2.8F, 0.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData water = modelPartData.addChild("water", ModelPartBuilder.create().uv(52, 51).cuboid(-1.5F, -10.5F, -1.5F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData water_out = modelPartData.addChild("water_out", ModelPartBuilder.create().uv(47, 61).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(27, 60).cuboid(-1.5F, 0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(WaterRocketEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		cone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bottle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		fins.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//fins_alternate.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

	}
	public void renderWater(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		water.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
	public void renderWaterStream(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		water_out.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
	public void renderLid(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		lid.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

}